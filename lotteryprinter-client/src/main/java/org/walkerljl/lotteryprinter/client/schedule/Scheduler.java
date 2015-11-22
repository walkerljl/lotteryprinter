package org.walkerljl.lotteryprinter.client.schedule;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.print.PrintService;

import org.walkerljl.lotteryprinter.client.entity.Task;
import org.walkerljl.lotteryprinter.client.enums.ConsumerState;
import org.walkerljl.lotteryprinter.client.enums.ProducerState;
import org.walkerljl.lotteryprinter.client.enums.SystemState;
import org.walkerljl.lotteryprinter.client.schedule.job.AddTaskByIdsJob;
import org.walkerljl.lotteryprinter.client.schedule.job.ConsumerJob;
import org.walkerljl.lotteryprinter.client.schedule.job.LoadExcelTaskJob;
import org.walkerljl.lotteryprinter.client.schedule.job.ProducerJob;

/**
 * 调度者
 * 
 * @author lijunlin
 */
public class Scheduler {
	/** 队列容量 */
	public final static int TASK_QUEUE_CAPACITY = 10;
	/** 一版彩票的张数 */
	public final static int COUNT_OF_PAGE = 12;
	/** 打印队列 */
	private BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<Task>(
			TASK_QUEUE_CAPACITY);
	/** 任务Map */
	private Map<Integer, Map<Long, Task>> backupMap = new ConcurrentHashMap<Integer, Map<Long, Task>>();
	/** sheet游标 */
	private AtomicInteger suffixOfSheet = new AtomicInteger(0);
	/** 打印服务线程池 */
	private ExecutorService threadPool = null;
	/** 系统状态 */
	private SystemState systemState = SystemState.INITIALIZATION;
	/** 生产者状态 */
	private ProducerState producerState = ProducerState.INITIALIZATION;
	/** 消费者状态 */
	private ConsumerState consumerState = ConsumerState.INITIALIZATION;
	/** 当前打印文件名 */
	private String currentFileName;
	/** 切换sheet标志 */
	private boolean canSwitch = false;

	/**
	 * 构造函数
	 */
	public Scheduler() {
		threadPool = Executors.newCachedThreadPool();
	}

	/**
	 * 加载Excel数据文件
	 * 
	 * @param path
	 * @param header
	 */
	public void loadExcel(String path, String[] header)
			throws ScheduleException {
		// 加载新的数据文件时，任务队列、备份Map中的数据清空
		if (taskQueue.size() > 0)
			taskQueue.clear();
		if (backupMap.size() > 0)
			backupMap.clear();
		// 重置下标参数
		suffixOfSheet = new AtomicInteger(0);

		// 设置当前打印文件名
		this.currentFileName = new File(path).getName();
		// 创建一个线程去加载Excel文件
		threadPool.execute(new LoadExcelTaskJob(this, path, header));
	}

	/*
	 * 开始生产任务
	 */
	public void startProduceTask() {
		// 修改标志位
		setSystemState(SystemState.RUNNING);
		setProducerState(ProducerState.PRODUCING);

		threadPool.execute(new ProducerJob(this));
	}

	/**
	 * 根据指定任务编号添加任务
	 * 
	 * @param idSet
	 */
	public void addTaskByIds(Set<Long> idSet) {
		// 修改标志位
		setSystemState(SystemState.RUNNING);
		setProducerState(ProducerState.PRODUCING);

		// 创建一个服务线程
		threadPool.execute(new AddTaskByIdsJob(this, idSet));
	}

	/**
	 * 处理任务
	 * 
	 * @param printServices
	 */
	public void startConsumeTask(List<PrintService> printServices) {
		// 当程序因为暂停操作而引发的重新创建线程处理任务，此时需要将系统状态设置为SystemState.RUNNING
		setSystemState(SystemState.RUNNING);
		// 修改标志位
		setConsumerState(ConsumerState.CONSUMNERING);

		// 为每一个打印服务创建线程去处理任务
		for (PrintService printService : printServices) {
			threadPool.execute(new ConsumerJob(printService, this));
		}
	}

	/**
	 * 暂停打印工作
	 */
	public void pause() {
		setSystemState(SystemState.PAUSE);
	}

	/**
	 * 切换sheet
	 */
	public void doSwitch() {
		if (canSwitch == true) {
			// 标志位改变
			canSwitch = false;
			// 获取下面的sheet中的数据生产任务
			startProduceTask();
		}
	}

	public BlockingQueue<Task> getTaskQueue() {
		return taskQueue;
	}

	public void setTaskQueue(BlockingQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public boolean isCanSwitch() {
		return canSwitch;
	}

	public void setCanSwitch(boolean canSwitch) {
		this.canSwitch = canSwitch;
	}

	public SystemState getSystemState() {
		return systemState;
	}

	public void setSystemState(SystemState systemState) {
		this.systemState = systemState;
	}

	public ProducerState getProducerState() {
		return producerState;
	}

	public void setProducerState(ProducerState producerState) {
		this.producerState = producerState;
	}

	public ConsumerState getConsumerState() {
		return consumerState;
	}

	public void setConsumerState(ConsumerState consumerState) {
		this.consumerState = consumerState;
	}

	public String getCurrentFileName() {
		return currentFileName;
	}

	public Map<Integer, Map<Long, Task>> getBackupMap() {
		return backupMap;
	}

	public void setBackupMap(Map<Integer, Map<Long, Task>> backupMap) {
		this.backupMap = backupMap;
	}

	public AtomicInteger getSuffixOfSheet() {
		return suffixOfSheet;
	}

}

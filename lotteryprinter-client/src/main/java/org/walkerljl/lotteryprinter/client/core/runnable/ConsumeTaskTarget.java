package org.walkerljl.lotteryprinter.client.core.runnable;

import javax.print.PrintService;

import org.walkerljl.lotteryprinter.client.common.LogUtils;
import org.walkerljl.lotteryprinter.client.common.PrintUtils;
import org.walkerljl.lotteryprinter.client.common.PropertiesUtils;
import org.walkerljl.lotteryprinter.client.core.ScheduleCenter;
import org.walkerljl.lotteryprinter.client.enums.ConsumerState;
import org.walkerljl.lotteryprinter.client.enums.ProducerState;
import org.walkerljl.lotteryprinter.client.enums.SystemState;
import org.walkerljl.lotteryprinter.client.pojo.Task;

/**
 * 消费者
 * 
 * @author lijunlin
 */
public class ConsumeTaskTarget implements Runnable
{
	/** 调度员*/
	private ScheduleCenter scheduler;
	/** 打印服务*/
	private PrintService printService;
	/** 配置文件路径*/
	private String filePath = "./config/task-assign-interval.properties";
	/** 任务分配时间间隔*/
	private int taskAssignInterval = 0;
	/** 日志*/
	private LogUtils logger = LogUtils.getInstance();
	
	/**
	 * 构造函数
	 * 
	 * @param printQueue
	 */
	public ConsumeTaskTarget(PrintService printService, ScheduleCenter scheduler)
	{
		this.printService = printService;
		this.scheduler = scheduler;
		
		//加载配置文件中任务分配时间间隔信息
		PropertiesUtils propertiesUtil = new PropertiesUtils(this.filePath);
		this.taskAssignInterval = Integer.parseInt(propertiesUtil.getValue("taskAssignInterval"));
	}
	
	@Override
	public void run() 
	{
		try
		{
			//为线程命名
			Thread.currentThread().setName(printService.getName());
			//打印工具对象
			PrintUtils printUtil = new PrintUtils();
			printUtil.setCurrentFileName(scheduler.getCurrentFileName());
			//是否继续
			boolean isContinue = true;
			//
			int errorCounter = 0;

			while(isContinue)
			{ 
				//防止过快将一个任务分配给打印机，导致打印机的任务队列太长
				Thread.sleep(this.taskAssignInterval*1000);
				//从队列当中获取一个任务
				Task task = scheduler.getTaskQueue().take();
				
				StringBuilder info = new StringBuilder();
				
				//如果当前任务处理出错，将此任务重新加入到任务队列中
				try
				{
					//处理任务
					printUtil.process(printService, task);
					//清零
					errorCounter = 0;
					logger.info(info.append("[").append(Thread.currentThread().getName()).append("]取得任务[")
							.append(task.getId()).append("]").toString());
				}
				catch(Exception ex)
				{
					errorCounter++;
					if(errorCounter <= 10)
					{
						//将出错的任务重新加入到任务队列当中
						scheduler.getTaskQueue().put(task);
						logger.error("任务["+task.getId()+"]打印出错，已经将此任务重新添加打队列中！");
					}
					else
					{
						logger.error("任务["+task.getId()+"]多次打印出错，已经将此任务抛弃！");
					}
				}
				
			    //接收到暂停命令，立即结束打印
			    if(scheduler.getSystemState() == SystemState.PAUSE)
			    	isContinue = false;
			    else if(scheduler.getTaskQueue().size() <= 0)
			    {
			    	//任务队列为空，并且并没有正在生产任务
			    	if(scheduler.getProducerState() != ProducerState.PRODUCING)
			    		isContinue = false;
			    }
			}
			
			if(scheduler.getSystemState() != SystemState.PAUSE)
			{

				//设置标志位
				if(scheduler.getSuffixOfSheet().intValue() > 0)
				{
					scheduler.setConsumerState(ConsumerState.CONSUMERED_ONE_SHEET);
					//切换标志还是为不能切换，这时再次检测是否能设置切换
					if(!scheduler.isCanSwitch())
						scheduler.setCanSwitch(true);
				}
				else
					scheduler.setConsumerState(ConsumerState.CONSUMERED_ALL_SHEET);
			}
			
		}
		catch(Exception ex)
		{
			//出现异常时修改标志位为ConsumerState.INITIALIZATION
			scheduler.setConsumerState(ConsumerState.INITIALIZATION);
			logger.error("打印服务["+ Thread.currentThread().getName() +"]异常终止，请查看相应设备！");
		}
		
	}
	
}

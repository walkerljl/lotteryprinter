package org.walkerljl.lotteryprinter.client.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 处理Excel工具
 * 
 * @author lijunlin
 */
public class ExcelUtils {
	/**
	 * 导入Excel文件并解析数据
	 * 
	 * @param path
	 */
	public static List<List<Object[]>> readExcel(String path, String[] header)
			throws ExcelUtilException {
		// 参数检查
		if (path == null || path.equals(""))
			throw new ExcelUtilException("您选择的Excel文件路径无效！");
		if (header == null || header.length <= 0)
			throw new ExcelUtilException("Excel文件头参数无效!");

		List<List<Object[]>> result = new ArrayList<List<Object[]>>();

		try {
			result = analysisExcel2007(path, header);
		} catch (Exception ex) {
			try {
				result = analysisExcel2003(path, header);
			} catch (Exception ex2) {
				throw new ExcelUtilException(ex.getMessage() + "\n"
						+ ex2.getMessage(), ex2);
			}
		}

		return result;
	}

	/**
	 * 解析Excel2003
	 * 
	 * @param path
	 * @param header
	 * @return
	 * @throws ExcelUtilException
	 */
	private static List<List<Object[]>> analysisExcel2003(String path,
			String[] header) throws ExcelUtilException {
		List<List<Object[]>> result = new ArrayList<List<Object[]>>();
		Workbook workbook = null;
		InputStream is = null;
		try {
			// 初始化workbook对象 2003
			is = new FileInputStream(path);
			workbook = new HSSFWorkbook(is);
			// 保存一个sheet中 的数据
			List<Object[]> aSheetList = null;
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				// 定义Sheet对象
				HSSFSheet aSheet = (HSSFSheet) workbook.getSheetAt(numSheets);
				if (aSheet == null)
					continue;
				aSheetList = new ArrayList<Object[]>();
				// 进入当前sheet的行的循环
				for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
						.getLastRowNum(); rowNumOfSheet++) {
					// 定义行，并赋值
					HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
					if (aRow == null)
						continue;
					/*
					 * 校验表头信息
					 */
					if (rowNumOfSheet == 0) {
						String errorMessage = "Excel文件头参数错误, 处理Excel版本： 2003!";
						for (int cellNumOfRow = 0; cellNumOfRow < header.length; cellNumOfRow++) {
							// 获得每一行每一列的值
							HSSFCell xCell = aRow.getCell(cellNumOfRow);
							if (xCell == null)
								throw new ExcelUtilException(errorMessage);
							String cellValue = getStringValue(xCell
									.getStringCellValue());
							if (!cellValue.equals(header[cellNumOfRow]))
								throw new ExcelUtilException(errorMessage);
						}
					} else// 开始解析Excel数据
					{
						// 定义一个Array，存放一行记录
						Object[] aRowData = new Object[header.length];
						// 数组下标
						int suffix = 0;
						// 读取rowNumOfSheet值所对应行的数据
						for (int cellNumOfRow = 0; cellNumOfRow < header.length; cellNumOfRow++) {
							// 获得每一行每一列的值
							HSSFCell xCell = aRow.getCell(cellNumOfRow);
							if (xCell == null) {
								suffix++;
								continue;
							}
							;
							int cellType = xCell.getCellType();

							if (cellType == XSSFCell.CELL_TYPE_STRING) {
								// 添加一个单元记录值
								aRowData[suffix] = getStringValue(xCell
										.getStringCellValue());
							} else if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {
								aRowData[suffix] = xCell.getNumericCellValue();
							} else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {
								aRowData[suffix] = xCell.getBooleanCellValue();
							}
							// 下标自增
							suffix++;
						}

						// 如果有任意一个单元的数据为空
						boolean isAdd = true;
						for (int i = 0; i < aRowData.length; i++) {
							if (aRowData[i] == null) {
								isAdd = false;
								break;
							}
						}

						// 判断是否添加一行记录
						if (isAdd)
							aSheetList.add(aRowData);

					}
				}

				// 添加一个sheet记录
				if (aSheetList.size() > 0)
					result.add(aSheetList);
			}
		} catch (ExcelUtilException ex) {
			throw new ExcelUtilException(ex.getMessage(), ex);
		} catch (Exception ex) {
			throw new ExcelUtilException("Excel文件解析时出错, 处理Excel版本： 2003!", ex);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				throw new ExcelUtilException(
						"Excel文件解析, 不能正常关闭输入输出流, 处理Excel版本： 2003!", ex);
			}
		}

		return result;
	}

	/**
	 * 解析Excel2007
	 * 
	 * @param path
	 * @param header
	 * @return
	 * @throws ExcelUtilException
	 */
	@SuppressWarnings("deprecation")
	private static List<List<Object[]>> analysisExcel2007(String path,
			String[] header) throws ExcelUtilException {
		List<List<Object[]>> result = new ArrayList<List<Object[]>>();
		Workbook workbook = null;
		try {
			// 初始化workbook对象 2007
			workbook = new XSSFWorkbook(path);
			List<Object[]> aSheetList = null;
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				// 定义Sheet对象
				XSSFSheet aSheet = (XSSFSheet) workbook.getSheetAt(numSheets);
				if (aSheet == null)
					continue;
				aSheetList = new ArrayList<Object[]>();

				// 进入当前sheet的行的循环
				for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
						.getLastRowNum(); rowNumOfSheet++) {
					// 定义行，并赋值
					XSSFRow aRow = aSheet.getRow(rowNumOfSheet);
					if (aRow == null)
						continue;
					/*
					 * 校验表头信息
					 */
					if (rowNumOfSheet == 0) {
						String errorMessage = "Excel文件头参数错误, 处理Excel版本： 2007!";
						for (int cellNumOfRow = 0; cellNumOfRow < header.length; cellNumOfRow++) {
							// 获得每一行每一列的值
							XSSFCell xCell = aRow.getCell(cellNumOfRow);
							if (xCell == null)
								throw new ExcelUtilException(errorMessage);
							String cellValue = getStringValue(xCell
									.getStringCellValue());
							if (!cellValue.equals(header[cellNumOfRow]))
								throw new ExcelUtilException(errorMessage);
						}
					} else// 开始解析Excel数据
					{
						// 定义一个Array，存放一行记录
						Object[] aRowData = new Object[header.length];
						// 数组下标
						int suffix = 0;
						// 读取rowNumOfSheet值所对应行的数据
						for (int cellNumOfRow = 0; cellNumOfRow < header.length; cellNumOfRow++) {
							// 获得每一行每一列的值
							XSSFCell xCell = aRow.getCell(cellNumOfRow);
							if (xCell == null) {
								suffix++;
								continue;
							}
							;
							int cellType = xCell.getCellType();
							if (cellType == XSSFCell.CELL_TYPE_STRING) {
								// 添加一个单元记录值
								aRowData[suffix] = getStringValue(xCell
										.getStringCellValue());
							} else if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {
								aRowData[suffix] = xCell.getNumericCellValue();
							} else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {
								aRowData[suffix] = xCell.getBooleanCellValue();
							}
							// 下标自增
							suffix++;
						}

						// 如果有任意一个单元的数据为空
						boolean isAdd = true;
						for (int i = 0; i < aRowData.length; i++) {
							if (aRowData[i] == null) {
								isAdd = false;
								break;
							}
						}

						// 判断是否添加一行记录
						if (isAdd)
							aSheetList.add(aRowData);
					}
				}

				// 添加一个sheet记录
				if (aSheetList.size() > 0)
					result.add(aSheetList);
			}
		} catch (ExcelUtilException ex) {
			throw new ExcelUtilException(ex.getMessage(), ex);
		} catch (Exception ex) {
			throw new ExcelUtilException("Excel文件解析时出错, 处理Excel版本： 2007!", ex);
		}

		return result;
	}

	/**
	 * 处理字符串
	 * 
	 * @param inputStr
	 * @return
	 */
	private static String getStringValue(String inputStr) {
		if (inputStr == null)
			return "";
		else
			return inputStr.replace('\t', ' ').replace('\n', ' ')
					.replace('\r', ' ').trim();
	}
}

package runner;

import org.json.simple.JSONObject;

import util.Xls_Reader;

public class ReadingXLS {

	public static void main(String[] args) {
		String filePath=System.getProperty("user.dir")+"\\src\\test\\resources\\testcases\\Testcases.xlsx";
		Xls_Reader xls = new Xls_Reader(filePath);
		String sheetName="Test_Titus";
		String flag="createnewcareplan";
		int iterationNumber=1;
		//int test = testgetTestDataSetsNew(filePath, flag,sheetName);
		//System.out.println("test: "+test);
		//JSONObject data =new ReadingXLS().getTestData(sheetName, flag, iterationNumber, filePath);
		//System.out.println(data);
		

	}
	
	public JSONObject getTestDataNew(String sheetName,String flag,int iterationNumber,String xlsFilePath) {
		int dataSetNumber=iterationNumber-1;
		System.out.println("dataSetNumber: "+ dataSetNumber);
		System.out.println("xlsFilePath:  "+ xlsFilePath);
		Xls_Reader xls = new Xls_Reader(xlsFilePath);
		int flagColumnNumber=0;
		int RowNumber=1;
		while(!xls.getCellData(sheetName, flagColumnNumber, RowNumber).equals(flag)) {
			flagColumnNumber++;
		}
		System.out.println("Flag Column Number: "+ flagColumnNumber);
		
//		int colNamesRowNumber = flagColumnNumber+1;
//		int dataStartRomNumber = flagColumnNumber+2;
		int testcaseNameColumnNumber=1;
		int testcaseNameRowNumber=2;
		int index=1;
		String testgetCelldata = xls.getCellData(sheetName, testcaseNameColumnNumber, testcaseNameRowNumber);
		System.out.println("testgetCelldata: " +testgetCelldata);
		while(!xls.getCellData(sheetName, testcaseNameColumnNumber, testcaseNameRowNumber).equals("")) {
			
			int colIndex=0;
			int rowNameKey=2;
			
			
			if(index == iterationNumber) {
				JSONObject json = new JSONObject();
				String testgetCelldata2 = xls.getCellData(sheetName, colIndex, rowNameKey);
				System.out.println("testgetCelldata2: " +testgetCelldata2);
				while(!xls.getCellData(sheetName, colIndex, rowNameKey).equals("")) {
					String colName=xls.getCellData(sheetName, colIndex, rowNameKey);
					String data = xls.getCellData(sheetName, colIndex+1+dataSetNumber, rowNameKey);
					
					System.out.println("Key:"+colName+" Value: "+data);
					json.put(colName, data);
					rowNameKey++;
				}
				return json;
			}else
				index++;
			
			System.out.println("-------");
			testcaseNameColumnNumber++;
		}
		return new JSONObject();
	}
	
	public JSONObject getTestData(String sheetName,String flag,int iterationNumber,String xlsFilePath) {
		System.out.println("xlsFilePath:  "+ xlsFilePath);
		Xls_Reader xls = new Xls_Reader(xlsFilePath);
		int flagRowNumber=1;
		while(!xls.getCellData(sheetName, 0, flagRowNumber).equals(flag)) {
			flagRowNumber++;
		}
		System.out.println("Flag Row Number "+ flagRowNumber);
		int colNamesRowNumber = flagRowNumber+1;
		int dataStartRomNumber = flagRowNumber+2;
		int index=1;
		while(!xls.getCellData(sheetName, 0, dataStartRomNumber).equals("")) {
			int colIndex=0;
			if(index == iterationNumber) {
				JSONObject json = new JSONObject();
				while(!xls.getCellData(sheetName, colIndex , colNamesRowNumber).equals("")) {
					String data = xls.getCellData(sheetName, colIndex, dataStartRomNumber);
					String colName=xls.getCellData(sheetName, colIndex, colNamesRowNumber);
					System.out.println(colName+" -- "+data);
					json.put(colName, data);
					colIndex++;
				}
				return json;
			}else
				index++;
			
			System.out.println("-------");
			dataStartRomNumber++;
		}
		return new JSONObject();
	}

	public int getTestDataSets(String xlsFilePath, String dataflag, String sheetName) {
		Xls_Reader xls = new Xls_Reader(xlsFilePath);
		int flagRowNumber=1;
		while(!xls.getCellData(sheetName, 0, flagRowNumber).equals(dataflag)) {
			flagRowNumber++;
		}
		System.out.println("Flag Row Number "+ flagRowNumber);
		
		int dataStartRomNumber = flagRowNumber+1;
		int totalRows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRomNumber).equals("")) {
			totalRows++;
			dataStartRomNumber++;
		}
		System.out.println("Total Rows " + totalRows);
		return totalRows;
	}
	
	public int testgetTestDataSetsNew(String xlsFilePath, String dataflag, String sheetName) {
		Xls_Reader xls = new Xls_Reader(xlsFilePath);
		int flagCloumnNumber=0;
		
		while(!xls.titusTestGetDatafromXLS(sheetName, 0, flagCloumnNumber).equals(dataflag)) {
			flagCloumnNumber++;
		}
		System.out.println("flag Cloumn Number "+ flagCloumnNumber);
		
		int dataStartRomNumber = flagCloumnNumber+1;
		int totalColumns=0;
		while(!xls.getCellData(sheetName, dataStartRomNumber, 2).equals("")) {
			totalColumns++;
			dataStartRomNumber++;
		}
		System.out.println("Total Columns " + totalColumns);
		return totalColumns;
	

}
}

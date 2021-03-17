package runner;

import util.Xls_Reader;

public class ReadingXLS {

	public static void main(String[] args) {
		
		String filePath = "C:\\Mamta\\Data.xlsx";
		//String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\xls_data\\Data.xlsx";
		System.out.println(filePath);
		Xls_Reader xls = new Xls_Reader(filePath);
		String flag = "deleteportfolio";
		String sheetName = "Portfolio Suit"; //should be same as suitName
		int flagRowNumber = 1;
		//String cellNumber = "A";
		while(!(xls.getCellData(sheetName, 0, flagRowNumber).equals(flag))) {
			String cellData = xls.getCellData(sheetName, 0, flagRowNumber);
			System.out.println("Flag row number ="+flagRowNumber+" cellData = "+cellData);
			flagRowNumber++;
			if(flagRowNumber==15)
				break;
		}
		System.out.println("Flag row number ="+flagRowNumber);
		
	}

}

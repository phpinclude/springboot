package kr.pe.withwind.exception;

public class ExcelGenException extends Exception {
	
	public static final int COLUMN_NAME_EMPTY = 100;
	public static final int DATE_FORMAT_INVALID = 200;
	public static final int NUMBER_FORMAT_INVALID = 201;
	public static final int OPTION_MAP_INVALID = 201;
	
	private int errCode;
	
	public ExcelGenException(int errCode){
		this.errCode=errCode;
	}

	@Override
	public String getMessage() {
		
		switch (errCode){
		case COLUMN_NAME_EMPTY:
			return "컬럼 이름 ArrayList가 비어 있습니다.";
		case DATE_FORMAT_INVALID:
			return "컬럼의 날짜 포멧이 일치 하지 않습니다.";
		case OPTION_MAP_INVALID:
			return "필드에 해당하는 옵션 맵은 존재하나 해당 맵 중에 data를 키로하는 값은 없습니다.";
		default :
			return "알수없는 에러";	
		}
	}
}

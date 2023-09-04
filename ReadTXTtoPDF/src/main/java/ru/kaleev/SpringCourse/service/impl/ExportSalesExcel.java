package ru.kaleev.SpringCourse.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.kaleev.SpringCourse.entity.User;
import ru.kaleev.SpringCourse.repository.UserRepository;
import ru.kaleev.SpringCourse.service.excel.DateUtil;


@Component
public class ExportSalesExcel extends ALargeExcelManager {
	@Autowired
	UserRepository userRepository;

	int numRow = 0;
	int cnt = -1;
	Map<String, Object> mapParam;
//	MessageSource messageSource;
	int salePrecision = 2;
	Locale locale;

//	@Autowired
//	ExportSaleService exportSaleService;

	public ExportSalesExcel() {
		super();
		mapParam = new HashMap<String, Object>();
	}

	private void addHeaderPage(XSSFSheet sheet, Map<String, CellStyle> styles) {
		numRow = 0;
		cnt = -1;
		styles.get("style6").setAlignment(HorizontalAlignment.LEFT);
		styles.get("style6").setVerticalAlignment(VerticalAlignment.TOP);
		styles.get("style5").setVerticalAlignment(VerticalAlignment.TOP);
		styles.get("formatNumber").setVerticalAlignment(VerticalAlignment.TOP);

		writeCell(sheet, numRow, ++cnt, "Report", styles.get("style1"));
		writeCell(sheet, ++numRow, cnt, "Date : ", styles.get("style3"));
		writeCell(sheet, numRow, ++cnt, DateUtil.date2String(new Date(), "dd/MM/yyyy HH:mm:ss"), styles.get("style1"));
		writeCell(sheet, ++numRow, --cnt, "Airline : ", styles.get("style3"));
		writeCell(sheet, numRow, ++cnt, "Vietnam Airline", styles.get("style1"));

		numRow++;
	}

	private void addColDetailUser(XSSFSheet sheet, Map<String, CellStyle> styles, int maxNumOfCoupon) {

		styles.get("boderright1").setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		styles.get("borderleft1").setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		styles.get("boderright1").setAlignment(HorizontalAlignment.RIGHT);
		// styles.get("boderright1").setVerticalAlignment(VerticalAlignment.CENTER);
		styles.get("boderright1").setWrapText(false);
		styles.get("borderleft1").setAlignment(HorizontalAlignment.LEFT);
		// styles.get("borderleft1").setVerticalAlignment(VerticalAlignment.CENTER);
		styles.get("borderleft1").setWrapText(false);
		numRow = numRow + 1;
		cnt = -1;

		writeCell(sheet, numRow, ++cnt, "IdTotal", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "UsernameTotal", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "Author", styles.get("borderleft1"));

		numRow++;
	}

	@SuppressWarnings("unchecked")
	private void addDetailUser(XSSFSheet sheet, Map<String, CellStyle> styles) {
		long totalAdmin = userRepository.countByAuthor("ADMIN");
		long totalUser = userRepository.countByAuthor("USER");

		styles.get("formatNumber").setWrapText(false);
		styles.get("style6").setWrapText(false);

			
			cnt = -1;
			//salePrecision = CurrencyDecimal.getPrecision(StringUtils.left(elm.getMonaie(), 3));
			writeCell(sheet, numRow, ++cnt, totalAdmin, styles.get("style6")); // Document
			writeCell(sheet, numRow, ++cnt, totalAdmin, styles.get("style6")); // Document
			writeCell(sheet, numRow, ++cnt, "ADMIN", styles.get("style6")); // Document
			numRow++;
			cnt = -1;
			//salePrecision = CurrencyDecimal.getPrecision(StringUtils.left(elm.getMonaie(), 3));
			writeCell(sheet, numRow, ++cnt, totalUser, styles.get("style6")); // Document
			writeCell(sheet, numRow, ++cnt, totalUser, styles.get("style6")); // Document
			writeCell(sheet, numRow, ++cnt, "USER", styles.get("style6")); // Document
			numRow++;	
	}

	private void addColUser(XSSFSheet sheet, Map<String, CellStyle> styles, int maxNumOfCoupon) {
		styles.get("boderright1").setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		styles.get("borderleft1").setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		styles.get("boderright1").setAlignment(HorizontalAlignment.RIGHT);
		// styles.get("boderright1").setVerticalAlignment(VerticalAlignment.CENTER);
		styles.get("boderright1").setWrapText(false);
		styles.get("borderleft1").setAlignment(HorizontalAlignment.LEFT);
		// styles.get("borderleft1").setVerticalAlignment(VerticalAlignment.CENTER);
		styles.get("borderleft1").setWrapText(false);
		numRow = 0;
		cnt = -1;
		
		writeCell(sheet, numRow, ++cnt, "Id", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "Username", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "Author", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "electricity_bill", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "subsistence_fee", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "tuition", styles.get("borderleft1"));
		writeCell(sheet, numRow, ++cnt, "Total", styles.get("borderleft1"));
		
		numRow++;
	}

	@SuppressWarnings("unchecked")
	private void addUser(XSSFSheet sheet, Map<String, CellStyle> styles) {
		List<User> listUser = userRepository.findAll();
		styles.get("formatNumber").setWrapText(false);
		styles.get("style6").setWrapText(false);
		for (User user : listUser) {
			cnt = -1;
			
			float totalCost = user.getElectricity_bill() + user.getSubsisyence_fee() + user.getTuition();
			
			writeCell(sheet, numRow, ++cnt, user.getId(), styles.get("style6")); 
			writeCell(sheet, numRow, ++cnt, user.getUsername(), styles.get("style6")); // related do
			writeCell(sheet, numRow, ++cnt, user.getAuthor(), styles.get("style6")); //
			writeCell(sheet, numRow, ++cnt, user.getElectricity_bill(),	styles.get("formatNumber")); 		
			writeCell(sheet, numRow, ++cnt, user.getSubsisyence_fee(), styles.get("formatNumber")); // 
			writeCell(sheet, numRow, ++cnt, user.getTuition(), styles.get("formatNumber")); //
			writeCell(sheet, numRow, ++cnt, totalCost, styles.get("formatNumber"));
			
			numRow++;
		}
	}

	public ByteArrayOutputStream exportExcel(Map<String, Object> mapParam) throws IOException {
		this.mapParam = mapParam;
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		SXSSFWorkbook wb = new SXSSFWorkbook(1000);
		XSSFWorkbook wbInstance = wb.getXSSFWorkbook();
		createStyles(wbInstance, styles);
		styles.get("boderright1").setAlignment(HorizontalAlignment.RIGHT);

		XSSFSheet sheetTkt = wbInstance.createSheet("Header sheet");
		sheetTkt.addIgnoredErrors(new CellRangeAddress(0, 99999, 0, 20), IgnoredErrorType.NUMBER_STORED_AS_TEXT);
		sheetTkt.setDisplayGridlines(false);

		addHeaderPage(sheetTkt, styles);

		try {
			addColDetailUser(sheetTkt, styles, 0);
			
			sheetTkt.setDefaultColumnWidth(14);
			addDetailUser(sheetTkt, styles);
			for (int k = 0; k <= cnt; k++) {
				try {
					// sheetTkt.trackColumnForAutoSizing(k);
					sheetTkt.autoSizeColumn(k, true);
					
					if (sheetTkt.getColumnWidth(k) < (10 * 256))
						sheetTkt.setColumnWidth(k, 10 * 256);
				} catch (Exception e) {
					
				}
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			XSSFSheet sheetEMD = wbInstance.createSheet("Body sheet");
			sheetEMD.addIgnoredErrors(new CellRangeAddress(0, 99999, 0, 20), IgnoredErrorType.NUMBER_STORED_AS_TEXT);
			sheetEMD.setDisplayGridlines(true);
			addColUser(sheetEMD, styles, 0);
			
			sheetEMD.setDefaultColumnWidth(14);
			addUser(sheetEMD, styles);
			for (int k = 0; k <= cnt; k++) {
				try {
					// sheetTkt.trackColumnForAutoSizing(k);
					sheetEMD.autoSizeColumn(k, true);
					
					if (sheetEMD.getColumnWidth(k) < (10 * 256))
						sheetEMD.setColumnWidth(k, 10 * 256);
				} catch (Exception e) {
					
				}
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}

		wb.write(stream);
		wb.close();//
		return stream;
	}

	public Map<String, Object> getMapParam() {
		return mapParam;
	}

	public void setMapParam(Map<String, Object> mapParam) {
		this.mapParam = mapParam;
	}

//	public void setMessageSource(MessageSource messageSource) {
//		this.messageSource = messageSource;
//	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

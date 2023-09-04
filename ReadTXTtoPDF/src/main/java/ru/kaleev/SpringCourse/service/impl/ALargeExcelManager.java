package ru.kaleev.SpringCourse.service.impl;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;

public abstract class ALargeExcelManager {
	private MessageSource messageSource;
	private Locale locale;

	@SuppressWarnings("deprecation")
	protected void createStyles(XSSFWorkbook workbook, Map<String, CellStyle> styles) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle style1 = defaultStyle(workbook);
		styles.put("style1", style1);

		CellStyle style2 = defaultStyle(workbook, arial(workbook, (short) 11, true));
		styles.put("style2", style2);

		CellStyle style3 = defaultStyle(workbook, arial(workbook, (short) 10, true));
		styles.put("style3", style3);

		CellStyle style4 = defaultStyle(workbook, arial(workbook, (short) 10, false));
		styles.put("style4", style4);

		CellStyle alignRight = defaultStyle(workbook, arial(workbook, (short) 10, false));
		alignRight.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("alignRight", alignRight);

		CellStyle style5 = defaultStyle(workbook, arial(workbook, (short) 10, false), HorizontalAlignment.RIGHT);
		styles.put("style5", style5);
	
		CellStyle style6 = defaultStyle(workbook, arial(workbook, (short) 10, false), HorizontalAlignment.LEFT);
		style6.setDataFormat(fmt.getFormat("#"));// set as text format
		styles.put("style6", style6);

		CellStyle formatNumber = formatNumber(workbook, arial(workbook, (short) 10, false), HorizontalAlignment.LEFT);
		formatNumber.setDataFormat(fmt.getFormat("#,##0.00"));
		styles.put("formatNumber", formatNumber);

		CellStyle style7 = defaultStyle1(workbook, arial(workbook, (short) 10, true));
		styles.put("style7", style7);

		CellStyle style8 = defaultStyle2(workbook, arial(workbook, (short) 10, true));
		styles.put("style8", style8);

		CellStyle style9 = defaultStyle(workbook, arial(workbook, (short) 10, false), HorizontalAlignment.LEFT);
		style9.setWrapText(true);
		styles.put("style9", style9);

		CellStyle formatNumber1 = formatNumber1(workbook, arial(workbook, (short) 9, true), HorizontalAlignment.RIGHT);
		styles.put("formatNumber1", formatNumber1);

		CellStyle crit = workbook.createCellStyle();
		crit.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		crit.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		crit.setFont(arial(workbook, (short) 10, false));
		styles.put("crit", crit);
		CellStyle boderleft = borderleft(workbook);
		styles.put("boderleft", boderleft);

		CellStyle borderleft1 = borderleft1(workbook);
		styles.put("borderleft1", borderleft1);

		CellStyle borderleft1OtherColor = borderleft1OtherColor(workbook);
		styles.put("borderleft1OtherColor", borderleft1OtherColor);

		CellStyle topbottom = bodertopbottom(workbook);
		styles.put("topbottom", topbottom);
		CellStyle boderright = borderright(workbook);
		styles.put("boderright", boderright);
		CellStyle boderright1 = borderright1(workbook);
		styles.put("boderright1", boderright1);

		CellStyle boderright1OtherColor = borderright1OtherColor(workbook);
		styles.put("boderright1OtherColor", boderright1OtherColor);

		CellStyle boderright2 = borderright2(workbook);
		styles.put("boderright2", boderright2);
		CellStyle boderall = borderall(workbook);
		styles.put("boderall", boderall);

		CellStyle blueRight = blueRight(workbook);
		styles.put("blueRight", blueRight);

		CellStyle blueLeft = blueLeft(workbook);
		styles.put("blueLeft", blueLeft);

		CellStyle noBorderRight = noBorderRight(workbook);
		styles.put("noBorderRight", noBorderRight);

		CellStyle noBorderLeft = noBorderLeft(workbook);
		styles.put("noBorderLeft", noBorderLeft);

		CellStyle headerString = headerString(workbook, arial(workbook, (short) 9, false), HorizontalAlignment.CENTER);
		styles.put("headerString", headerString);
		CellStyle headerNumber = headerNumber(workbook, arial(workbook, (short) 9, false), HorizontalAlignment.RIGHT);
		styles.put("headerNumber", headerNumber);
		CellStyle bodyString = bodyString(workbook, arial(workbook, (short) 9, false), HorizontalAlignment.LEFT);
		styles.put("bodyString", bodyString);
		CellStyle bodyNumber = bodyNumber(workbook, arial(workbook, (short) 9, false), HorizontalAlignment.RIGHT);
		styles.put("bodyNumber", bodyNumber);

	}

	private CellStyle borderleft(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName("Arial");
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		// style.set
		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);
		/*
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle borderright(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);
		/*
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle borderright1(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle borderright1OtherColor(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	/* 02Dec2015_Background color, Font Bold */
	private CellStyle borderright2(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(true);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle borderleft1(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.index);
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.index);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	public CellStyle customizeCellStyle(XSSFWorkbook workbook, boolean bold, HorizontalAlignment align,
			BorderStyle borderLeft, BorderStyle borderRight, BorderStyle borderTop, BorderStyle borderBottom) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();

		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(bold);
		style.setFont(font);
		style.setAlignment(align);
		style.setBorderLeft(borderLeft);
		style.setBorderRight(borderRight);
		style.setBorderTop(borderTop);
		style.setBorderBottom(borderBottom);

		style.setLeftBorderColor(IndexedColors.BLACK.index);
		style.setRightBorderColor(IndexedColors.BLACK.index);
		style.setTopBorderColor(IndexedColors.BLACK.index);
		style.setBottomBorderColor(IndexedColors.BLACK.index);

		return style;
	}

	private CellStyle borderleft1OtherColor(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle bodertopbottom(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);
		/*
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle borderall(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.LIGHT_BLUE.index);
		/*
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	protected void writeCell(Sheet sheet, int row, int col, String value, CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		cell.setCellType(CellType.STRING);
		if (style != null)
			cell.setCellStyle(style);

	}

	protected void writeCell(Sheet sheet, int row, int col, Date value, CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		if (style != null)
			cell.setCellStyle(style);
	}

	protected void writeCell(Sheet sheet, int row, int col, double value, CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		if (style != null)
			cell.setCellStyle(style);
	}

	protected void writeCell(XSSFWorkbook workbook, Sheet sheet, int row, int col, String value, int colspan) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		sheet.addMergedRegion(new CellRangeAddress(row, row, (short) col, (short) (col + colspan)));
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (style != null)
			cell.setCellStyle(style);
	}

	protected void writeCellOtherColor(XSSFWorkbook workbook, Sheet sheet, int row, int col, String value,
			int colspan) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		sheet.addMergedRegion(new CellRangeAddress(row, row, (short) col, (short) (col + colspan)));
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (style != null)
			cell.setCellStyle(style);
	}

	protected void writeCellHeader(XSSFWorkbook workbook, Sheet sheet, int row, int col, String value, int colspan) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		sheet.addMergedRegion(new CellRangeAddress(row, row, (short) col, (short) (col + colspan)));

		if (style != null)
			cell.setCellStyle(style);
	}

	protected Cell getCell(Sheet sheet, int row, int col) {
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		Cell cell = sheetRow.getCell((short) col);
		if (cell == null) {
			cell = sheetRow.createCell((short) col);
		}
		return cell;
	}

	/**
	 * Arial 10 align-left
	 * 
	 * @param workbook
	 * @return
	 */
	private CellStyle defaultStyle(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setFont(defaultFont(workbook));
		return style;
	}

	private CellStyle defaultStyle(XSSFWorkbook workbook, Font font) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);

		return style;
	}

	private CellStyle defaultStyle1(XSSFWorkbook workbook, Font font) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);

		return style;
	}

	private CellStyle defaultStyle2(XSSFWorkbook workbook, Font font) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);

		return style;
	}

	private CellStyle defaultStyle(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.index);
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.index);
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.index);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.index);
		return style;
	}

	private Font defaultFont(XSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		return font;
	}

	private Font arial(XSSFWorkbook workbook, short height, boolean boldweight) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints(height);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(boldweight);
		return font;
	}

	protected void writeCellNumber(Sheet sheet, int row, int col, double value, CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		if (style != null)
			cell.setCellStyle(style);
	}

	private CellStyle formatNumber(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.index);
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.index);
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.index);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.index);
		return style;
	}

	/* 02Dec2015_Background color, Number Bold */
	private CellStyle formatNumber1(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		/* HSSFPalette palette = workbook.getCustomPalette(); */
		DataFormat format = workbook.createDataFormat();

		style.setDataFormat(format.getFormat("0"));
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(true);

		// replacing the standard red with freebsd.org red
		/*
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 * palette.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 218, //RGB red
		 * 141,180,226 (byte) 238, //RGB green (byte) 243 //RGB blue );
		 */

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
		//

	}

	private CellStyle blueRight(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	private CellStyle noBorderRight(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		return style;
	}

	private CellStyle noBorderLeft(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		return style;
	}

	private CellStyle blueLeft(XSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		Font font = workbook.createFont();
		style.setFont(font);
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setBold(false);
		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	public static void headingStyleCenter(XSSFWorkbook wb, Sheet sheet, Cell cell, String headingValue,
			CellRangeAddress region) {
		cell.setCellValue(headingValue);
		sheet.addMergedRegion(region);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.index);
//		cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
		Font font = wb.createFont();
		font.setFontName("ARIAL");
		font.setBold(true);
		font.setFontHeightInPoints((short) 14);
//		font.setColor(IndexedColors.WHITE.index);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	protected void writeCellNumber(XSSFWorkbook workbook, Sheet sheet, int row, int col, double value,
			CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		if (style != null)
			cell.setCellStyle(style);
	}

	protected void writeCell(XSSFWorkbook workbook, Sheet sheet, int row, int col, String value, int colspan,
			CellStyle style) {
		Cell cell = getCell(sheet, row, col);
		cell.setCellValue(value);
		style.setAlignment(HorizontalAlignment.CENTER);
		CellRangeAddress range = new CellRangeAddress(row, row, col, col + colspan);
		sheet.addMergedRegion(range);

		RegionUtil.setBorderBottom(style.getBorderBottom(), range, sheet);
		RegionUtil.setBorderTop(style.getBorderTop(), range, sheet);
		RegionUtil.setBorderLeft(style.getBorderLeft(), range, sheet);
		RegionUtil.setBorderRight(style.getBorderRight(), range, sheet);

		RegionUtil.setBottomBorderColor(style.getBottomBorderColor(), range, sheet);
		RegionUtil.setTopBorderColor(style.getTopBorderColor(), range, sheet);
		RegionUtil.setRightBorderColor(style.getRightBorderColor(), range, sheet);
		RegionUtil.setLeftBorderColor(style.getLeftBorderColor(), range, sheet);

		/*
		 * HSSFPalette palette = workbook.getCustomPalette(); //replacing the standard
		 * red with freebsd.org red
		 * palette.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 141, //RGB red
		 * 141,180,226 (byte) 180, //RGB green (byte) 226 //RGB blue );
		 */
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		if (style != null)
			cell.setCellStyle(style);
	}

	private CellStyle headerString(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		/*
		 * HSSFPalette bgHeader = workbook.getCustomPalette(); //Color border
		 * bgHeader.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 91, (byte)
		 * 155, (byte) 213); //Color foreground
		 * bgHeader.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 180, (byte)
		 * 204, (byte) 219);
		 */
		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.PALE_BLUE.index);

		if (font == null)
			style.setFont(defaultFont(workbook));
		else {
			font.setColor(IndexedColors.WHITE.index);
			style.setFont(font);
		}

		style.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;
	}

	private CellStyle bodyString(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		/*
		 * HSSFPalette bgHeader = workbook.getCustomPalette(); //Color border
		 * bgHeader.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 91, (byte)
		 * 155, (byte) 213);
		 */
		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.PALE_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.PALE_BLUE.index);

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);
		return style;
	}

	private CellStyle headerNumber(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		/*
		 * HSSFPalette bgHeader = workbook.; //Color border
		 * bgHeader.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 91, (byte)
		 * 155, (byte) 213); //Color foreground
		 * bgHeader.setColorAtIndex(IndexedColors.LIGHT_BLUE.index, (byte) 180, (byte)
		 * 204, (byte) 219);
		 * 
		 * workbook.
		 */
		DataFormat format = workbook.createDataFormat();

		style.setDataFormat(format.getFormat("#,##0.00"));

		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);

		style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;
	}

	private CellStyle bodyNumber(XSSFWorkbook workbook, Font font, HorizontalAlignment alignment) {
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(alignment);
		// HSSFPalette bgHeader = workbook.getXSSFWorkbook().getCustomPalette();
		// Color border
		// bgHeader.setColorAtIndex(IndexedColors.PALE_BLUE.index, (byte) 91, (byte)
		// 155, (byte) 213);
		// style.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0,
		// 128)).getIndex());

		DataFormat format = workbook.createDataFormat();

		style.setDataFormat(format.getFormat("#,##0.00"));

		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.LIGHT_BLUE.index);
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.LIGHT_BLUE.index);

		if (font == null)
			style.setFont(defaultFont(workbook));
		else
			style.setFont(font);

		return style;
	}

	public static void setBackgroundRegion(CellRangeAddress region, Sheet sheet, XSSFWorkbook wb) {
		int rowStart = region.getFirstRow();
		int rowEnd = region.getLastRow();
		int column = region.getFirstColumn();
		int columnEnd = region.getLastColumn();
		for (int i = rowStart; i <= rowEnd; i++) {
			for (int j = column; j <= columnEnd; j++) {
				if (sheet.getRow(i).getCell(j) == null) {
					setStyleCell(wb, sheet.getRow(i).createCell(j));
				} else {
					setStyleCell(wb, sheet.getRow(i).getCell(j));
				}
			}
		}
	}

	public static void setStyleCell(XSSFWorkbook wb, Cell cell) {
		CellStyle cellStyle = copy(cell.getCellStyle(), wb);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);
	}

	public static CellStyle copy(CellStyle style, XSSFWorkbook wb) {
		CellStyle style2 = wb.createCellStyle();
		style2.setFont(wb.getFontAt(style.getFontIndex()));
		style2.setVerticalAlignment(style.getVerticalAlignment());
		style2.setAlignment(style.getAlignment());
		style2.setWrapText(style.getWrapText());
		style2.setBorderBottom(style.getBorderBottom());
		style2.setBorderTop(style.getBorderTop());
		style2.setBorderRight(style.getBorderLeft());
		style2.setBorderLeft(style.getBorderLeft());
		style2.setBottomBorderColor(style.getBottomBorderColor());
		style2.setFillForegroundColor(style.getFillForegroundColor());
		style2.setFillPattern(style.getFillPattern());
		return style2;
	}

	public static void borderAllWithColor(CellStyle style, short color) {
		style.setBottomBorderColor(color);
		style.setTopBorderColor(color);
		style.setLeftBorderColor(color);
		style.setRightBorderColor(color);
	}

	public static void borderAll(CellStyle style, BorderStyle borderStyle) {
		style.setBorderBottom(borderStyle);
		style.setBorderTop(borderStyle);
		style.setBorderLeft(borderStyle);
		style.setBorderRight(borderStyle);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

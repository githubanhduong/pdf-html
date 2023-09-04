package ru.kaleev.SpringCourse.service;

import javax.servlet.http.HttpServletResponse;


public interface PdfPageService {
	void exportPDFAll(HttpServletResponse response, String keyword, int page, int size, String[] sort);  
}

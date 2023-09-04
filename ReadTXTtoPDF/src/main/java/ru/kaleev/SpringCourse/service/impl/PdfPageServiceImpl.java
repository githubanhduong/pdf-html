package ru.kaleev.SpringCourse.service.impl;

import ru.kaleev.SpringCourse.service.PdfPageService;

import javax.faces.context.ExceptionHandler;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import ru.kaleev.SpringCourse.entity.User;
import ru.kaleev.SpringCourse.repository.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfPageServiceImpl implements PdfPageService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	HeaderFooterPageEvent headerFooterPageEvent;

	@Autowired
	SpringResourceTemplateResolver templateResolver;

	@Autowired
	SpringTemplateEngine templateEngine;

	@Override
	public void exportPDFAll(HttpServletResponse response, String keyword, int page, int size, String[] sort) {
		String sortField = sort[0];
		String sortDirection = sort[1];
		Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Order order = new Order(direction, sortField);

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
		Page<User> userPage;
		List<User> listUser = null;

		Document document = new Document(PageSize.A4, 36, 36, 90, 36);

		try {
			ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);
			writer.setPageEvent(headerFooterPageEvent);
			// Set the file name and location
			PdfWriter.getInstance(document, new FileOutputStream("user-list.pdf"));
			document.open();

			PdfPTable table = null;
			document.add(new Paragraph("List User"));
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			do {
				if (keyword == null) {
					userPage = userRepository.findAll(pageable);
				} else {
					userPage = userRepository.findByAuthorContaining(keyword, pageable);
				}
				document.newPage();
				listUser = userPage.getContent();

				table = new PdfPTable(3);
				table.setWidthPercentage(100);
				table.setSpacingBefore(10f);
				table.setSpacingAfter(10f);

				PdfPCell headerCell1 = new PdfPCell(new Phrase("ID", headerFont));
				PdfPCell headerCell2 = new PdfPCell(new Phrase("Name", headerFont));
				PdfPCell headerCell3 = new PdfPCell(new Phrase("Author", headerFont));
				table.addCell(headerCell1);
				table.addCell(headerCell2);
				table.addCell(headerCell3);

				Font cellFont = FontFactory.getFont(FontFactory.HELVETICA);

				for (User user : listUser) {
					table.addCell(new PdfPCell(new Phrase(String.valueOf(user.getId()), cellFont)));
					table.addCell(new PdfPCell(new Phrase(user.getUsername(), cellFont)));
					table.addCell(new PdfPCell(new Phrase(user.getAuthor(), cellFont)));
				}
				document.add(table);
				pageable = userPage.nextPageable();
			} while (userPage.hasNext());
			// Close the document
			document.close();
			// Set the content type and attachment header
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=user_list.pdf");

			// Write the PDF to the response output stream
			OutputStream outputStream = response.getOutputStream();
			baosPDF.writeTo(outputStream);
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generatePdfFromHtml(HttpServletRequest request, HttpServletResponse response, String html,
			@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "4") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(parseThymeleafTemplate(request, response, html, keyword, page, size, sort));
			renderer.layout();
			renderer.createPDF(outputStream);

			// Set the response headers
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"html.pdf\"");

			// Write the PDF bytes to the response output stream
			OutputStream out = response.getOutputStream();
			outputStream.writeTo(out);
			out.flush();
			out.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String parseThymeleafTemplate(HttpServletRequest request, HttpServletResponse response, String html,
			@RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "4") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {

		Context context = new Context();

		List<User> listUser = new ArrayList<User>();

		String sortField = sort[0];
		String sortDirection = sort[1];
		Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Order order = new Order(direction, sortField);

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
		Page<User> userPage;
		if (keyword == null) {
			userPage = userRepository.findAll(pageable);

		} else {
			userPage = userRepository.findByAuthorContaining(keyword, pageable);
			context.setVariable("keyword", keyword);
		}

		listUser = userPage.getContent();
		
		for (int i = 1; i <= userPage.getTotalPages(); i++) {
			context.setVariable("listUsers_" + i, listUser);
			if (i == userPage.getTotalPages()) break;
			pageable = userPage.nextPageable();
			if (keyword == null) {
				userPage = userRepository.findAll(pageable);
			} else {
				userPage = userRepository.findByAuthorContaining(keyword, pageable);
			}
			listUser = userPage.getContent();
		} 
		
		
		context.setVariable("totalPages", userPage.getTotalPages());
		context.setVariable("totalItems", userPage.getTotalElements());
		context.setVariable("pageSize", size);
		context.setVariable("sortField", sortField);
		context.setVariable("sortDirection", sortDirection);
		context.setVariable("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

		try {
			String contentPage = templateEngine.process(html, context);
			if (contentPage != null) {
				return contentPage;
			} else {
				return "";
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}

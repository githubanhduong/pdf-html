package ru.kaleev.SpringCourse.controllers;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ru.kaleev.SpringCourse.entity.Agent;
import ru.kaleev.SpringCourse.entity.Airline;
import ru.kaleev.SpringCourse.entity.Commission;
import ru.kaleev.SpringCourse.entity.User;
import ru.kaleev.SpringCourse.repository.UserRepository;
import ru.kaleev.SpringCourse.service.impl.ExportSalesExcel;
import ru.kaleev.SpringCourse.service.impl.PdfPageServiceImpl;

import org.springframework.ui.ModelMap;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystemNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class FirstController {
	public static final String FONT = "font/FreeSans.ttf";

	@Autowired
	Airline airline;

	@Autowired
	Commission commission;

	@GetMapping("/success")
	public String successPage(ModelMap model, @RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {

		return "first/hello";
	}

	@PostMapping(value = "/exportPDF")
	public void exportPDF(HttpServletResponse response, @RequestParam("filename1") MultipartFile filename1,
			@RequestParam("filename2") MultipartFile filename2, @RequestParam("filename3") MultipartFile filename3)
			throws Exception {
		exportPDFAir(response, filename1, filename2, filename3);

		List<Commission> listCommission = new ArrayList<Commission>();
		List<Airline> listAirline = new ArrayList<Airline>();
		List<Agent> listAgent = new ArrayList<Agent>();
		
		if (!filename1.isEmpty()) {
			String contentF1 = new Scanner(filename1.getInputStream()).useDelimiter("\\A").next();

//             System.out.println("Content of " + filename1.getOriginalFilename() + ":");
//             System.out.println(content);
			try {
				Scanner scanner = new Scanner(contentF1);

				while (scanner.hasNextLine()) {
					Commission commission = new Commission();
					String line  = scanner.nextLine();
					commission.setCodeAirline(line.substring(0, 3));        // "072"
					commission.setIdentify(line.substring(3, 14));          // "00002029612"
			        String part3 = line.substring(14, line.length());
			        commission.setMoney(part3.replaceFirst("^0+", ""));     // Remove leading zeros
			        listCommission.add(commission);
				}

				scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!filename2.isEmpty()) {
			String contentF2 = new Scanner(filename2.getInputStream()).useDelimiter("\\A").next();
			
			try {
	            Scanner fileScanner = new Scanner(contentF2);

	            while (fileScanner.hasNextLine()) {
	            	Airline airline = new Airline();
	            	String lineIAST2901 = fileScanner.nextLine(); // Read the first line
	            	
	            	if (lineIAST2901.contains("IAST2901")) {
	            		airline.setNameAirline(lineIAST2901.substring(0, 20));
	            		airline.setCodeAirline(lineIAST2901.substring(lineIAST2901.length() - 3, lineIAST2901.length()));
	            		airline.setAddress1(lineIAST2901.substring(20, 40));
	            		airline.setAddress2(lineIAST2901.substring(40, lineIAST2901.length() - 11));
	            	}
	            	if (!fileScanner.hasNextLine()) {
	                    break; // If no more lines, break the loop
	                }

	            	String lineIAST2902 = fileScanner.nextLine(); // Read the second line

	            	if (lineIAST2902.contains("IAST2902")) {
	                	airline.setTelephone(lineIAST2902.substring(0, 14));
	                	airline.setIdentify(lineIAST2902.substring(14, 28));
	                	airline.setYear(Integer.parseInt("20" + lineIAST2902.substring(40, 42)));
	                	listAirline.add(airline);
	                }
	            }

	            fileScanner.close();
	        } catch (FileSystemNotFoundException e) {
	            e.printStackTrace();
	        }
		}

		if (!filename3.isEmpty()) {
			String contentF3 = new Scanner(filename3.getInputStream()).useDelimiter("\\A").next();
			
			try {
				Scanner fileScannerF3 = new Scanner(contentF3);
				
				while (fileScannerF3.hasNextLine()) {
					Agent agent = new Agent();
					String line = fileScannerF3.nextLine();
					agent.setCodeAgent(line.split("\\|")[0]);
					agent.setNomAgent(line.split("\\|")[1]);
					agent.setAddress1(line.split("\\|")[2]);
					agent.setAddress2(line.split("\\|")[3]);
					agent.setAddress3(line.split("\\|")[4]);
					
					listAgent.add(agent);
				}
				
				fileScannerF3.close();
			} catch (FileSystemNotFoundException e) {
				e.printStackTrace();
			}
		}

		Document document = new Document(PageSize.A4);
		Font fontText = FontFactory.getFont(FontFactory.COURIER, 7);
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baosPDF);
		PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);

		document.open();
		document.setPageSize(PageSize.A4);
		document.newPage();
		
		Airline airlineCommision = new Airline();
		Agent agentCommision = new Agent();
		int i = 1;
		for (Commission commission : listCommission) {
			Optional<Airline> matchingAirline = listAirline.stream()
					.filter(airline -> airline.getCodeAirline().equals(commission.getCodeAirline()))
					.findFirst();
			
			airlineCommision = matchingAirline.get();

			Optional<Agent> matchingAgent = listAgent.stream()
					.filter(agent -> agent.getCodeAgent().equals(commission.getIdentify()))
					.findFirst();

			if (matchingAgent.isPresent()) {
			    agentCommision = matchingAgent.get();
			} else {
				agentCommision.setAddress1("");
				agentCommision.setAddress2("");
				agentCommision.setAddress3("");
				agentCommision.setNomAgent("");
			}

			int year              = airlineCommision.getYear();
			String telephone      = airlineCommision.getTelephone();
			String numberNom      = commission.getIdentify();
			String identification = airlineCommision.getIdentify();
			
			String airlineInput   = airlineCommision.getNameAirline();
			String adressInput3   = airlineCommision.getAddress2();
			String adressInput4   = airlineCommision.getAddress1();
			String adressInput    = agentCommision.getAddress1();
			String adressInput1   = agentCommision.getAddress2();
			String adressInput2   = agentCommision.getAddress3();
			String nomAgentFormat = agentCommision.getNomAgent();
			
			int money = 0;
			if (!commission.getMoney().isEmpty()) {
				money = Integer.valueOf(commission.getMoney());
			} else {
				money = 0;
			}
			
			String adress = String.format("%-35s", adressInput);
			String adress1 = String.format("%-35s", adressInput1);
			String adress2 = String.format("%-35s", adressInput2);
//			String trimmedNumber = numberNom.replaceFirst("^0+", ""); // Remove leading zeros
			String formattedNumber1 = String.format("%12s", numberNom);
//			String formattedNumber2 = String.format("%8s", trimmedNumber);
			String formattedMoney = String.format("%12s", new DecimalFormat("###.###").format(money));
			String nomAgent = String.format("%-27s", nomAgentFormat.substring(0, Math.min(nomAgentFormat.length(), 26)));
			
			String hyphenString0 = new String(new char[78]).replace('\0', '\u002D');
			String hyphenString1 = new String(new char[06]).replace('\0', '\u002D');
			String hyphenString2 = new String(new char[40]).replace('\0', '\u002D');
			String hyphenString3 = new String(new char[37]).replace('\0', '\u002D');
			String hyphenString4 = new String(new char[17]).replace('\0', '\u002D');

			document.newPage();
			String formattedNumber0 = String.format("%5d", i);
			
			document.add(new Phrase(String.valueOf(
					"I M P O T S  " + year + " \u002D "
							+ "ETAT DES HONORAIRES,VACATIONS,COURTAGES,COMMISSIONS              D A S   A G E N C E S   (DAS II)\n"
							+ "                             RISTOURNES ET JETONS DE PRESENCE                       (DECLARATION AGENT)      (EX. 2460-"
							+ "1024)\n" + "                      DROITS D’AUTEUR ET D’INVENTEUR PAYES PENDANT L’ANNEE\n" + ""),
					fontText));
			
			document.add(new Phrase(String.valueOf(
					"                                                                                RAISON SOCIALE : "
							+ airlineInput + "\n" + "   LE CADRE CI" + "\u002D"
							+ "APRES EST RESERVE A LA DECLARATION DES SOMMES CI" + "\u002D"
							+ "DESSUS          ADRESSE        :  " + adressInput4 + "\n"
							+ "   VISEES QUI ONT ETE VERSEES A DES PERSONNES N O N  S A L A R I E E S                           "
							+ adressInput3 + "\n"
							+ "                                                                                TELEPHONE      : " + telephone + "\n"
							+ "                                                                                NO POSTE RESPONSABLE :\n"
							+ "                                                                                NO IDENTIFICATION    : "
							+ identification + "\n"),
					fontText));
			
			document.add(new Phrase(String.valueOf(
					"          ********************************************************************************************************\n"
							+ "           NO   I 	    D E S I G N A T I O N     D E S      B E N E F I C I A I R E S           I MONTANT DES     I\n"
							+ "          ORDRE I" + hyphenString0 + "I COMMISSIONS (5) I\n"
							+ "                I      N    O    M  (1)                  I    A D R E S S E    (3)             I RISTOURNES (EUR)I\n"
							+ "          " + hyphenString1 + "I" + hyphenString2 + "I" + hyphenString3 + "I"
							+ hyphenString4 + "I\n"),
					fontText));
			
			document.add(new Phrase(String.valueOf(
					"          " + formattedNumber0 + " I" + formattedNumber1 + " "
					+ nomAgent + "I " + adress + " I " + formattedMoney + "    I\n"
					+ "               " + " I                                        " + "I " + adress1
					+ " I                " + " I\n" + "               " + " I                                        "
					+ "I " + adress2 + " I                " + " I\n"
					+ "          ******I****************************************I*************************************I*****************I\n"),
					fontText));
			
			++i;
			
		}
		

//			for (String iastNumber : listIastNumber) {
//				document.add(new Phrase(String.valueOf(iastNumber), fontText));
//			}

		document.close();

		// Set the content type and attachment header
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename="+ airlineCommision.getCodeAirline() + ".DW_DASAGT.Commission.imp.pdf");

		// Write the PDF to the response output stream
		OutputStream outputStream = response.getOutputStream();
		baosPDF.writeTo(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	public void exportPDFAir(HttpServletResponse response, @RequestParam("filename1") MultipartFile filename1,
			@RequestParam("filename2") MultipartFile filename2, @RequestParam("filename3") MultipartFile filename3)
					throws Exception {
		double subtotalPage = 0;
		
		List<Commission> listCommission = new ArrayList<Commission>();
		List<Airline> listAirline = new ArrayList<Airline>();
		List<Agent> listAgent = new ArrayList<Agent>();
		
		if (!filename1.isEmpty()) {
			String contentF1 = new Scanner(filename1.getInputStream()).useDelimiter("\\A").next();
			
//             System.out.println("Content of " + filename1.getOriginalFilename() + ":");
//             System.out.println(content);
			try {
				Scanner scanner = new Scanner(contentF1);
				
				while (scanner.hasNextLine()) {
					Commission commission = new Commission();
					String line  = scanner.nextLine();
					commission.setCodeAirline(line.substring(0, 3));        // "072"
					commission.setIdentify(line.substring(3, 14));          // "00002029612"
					String part3 = line.substring(14, line.length());
					commission.setMoney(part3.replaceFirst("^0+", ""));     // Remove leading zeros
					listCommission.add(commission);
				}
				
				scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!filename2.isEmpty()) {
			String contentF2 = new Scanner(filename2.getInputStream()).useDelimiter("\\A").next();
			
			try {
				Scanner fileScanner = new Scanner(contentF2);
				
				while (fileScanner.hasNextLine()) {
					Airline airline = new Airline();
					String lineIAST2901 = fileScanner.nextLine(); // Read the first line
					
					if (lineIAST2901.contains("IAST2901")) {
						airline.setNameAirline(lineIAST2901.substring(0, 20));
						airline.setCodeAirline(lineIAST2901.substring(lineIAST2901.length() - 3, lineIAST2901.length()));
						airline.setAddress1(lineIAST2901.substring(20, 40));
						airline.setAddress2(lineIAST2901.substring(40, lineIAST2901.length() - 11));
					}
					if (!fileScanner.hasNextLine()) {
						break; // If no more lines, break the loop
					}
					
					String lineIAST2902 = fileScanner.nextLine(); // Read the second line
					
					if (lineIAST2902.contains("IAST2902")) {
						airline.setTelephone(lineIAST2902.substring(0, 14));
						airline.setIdentify(lineIAST2902.substring(14, 28));
						airline.setYear(Integer.parseInt("20" + lineIAST2902.substring(40, 42)));
						listAirline.add(airline);
					}
				}
				
				fileScanner.close();
			} catch (FileSystemNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (!filename3.isEmpty()) {
			String contentF3 = new Scanner(filename3.getInputStream()).useDelimiter("\\A").next();
			
			try {
				Scanner fileScannerF3 = new Scanner(contentF3);
				
				while (fileScannerF3.hasNextLine()) {
					Agent agent = new Agent();
					String line = fileScannerF3.nextLine();
					agent.setCodeAgent(line.split("\\|")[0]);
					agent.setNomAgent(line.split("\\|")[1]);
					agent.setAddress1(line.split("\\|")[2]);
					agent.setAddress2(line.split("\\|")[3]);
					agent.setAddress3(line.split("\\|")[4]);
					
					listAgent.add(agent);
				}
				
				fileScannerF3.close();
			} catch (FileSystemNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Document document = new Document(PageSize.A4);
		Font fontText = FontFactory.getFont(FontFactory.COURIER, 7);
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baosPDF);
		PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
		
		document.open();
		document.setPageSize(PageSize.A4);
		document.newPage();
		
		Airline airlineCommision = new Airline();
		Agent agentCommision = new Agent();
		int i = 1;
		for (Commission commission : listCommission) {
			Optional<Airline> matchingAirline = listAirline.stream()
					.filter(airline -> airline.getCodeAirline().equals(commission.getCodeAirline()))
					.findFirst();
			
			airlineCommision = matchingAirline.get();
			
			Optional<Agent> matchingAgent = listAgent.stream()
					.filter(agent -> agent.getCodeAgent().equals(commission.getIdentify()))
					.findFirst();
			
			if (matchingAgent.isPresent()) {
				agentCommision = matchingAgent.get();
			} else {
				agentCommision.setAddress1("");
				agentCommision.setAddress2("");
				agentCommision.setAddress3("");
				agentCommision.setNomAgent("");
			}
			
			int year              = airlineCommision.getYear();
			String telephone      = airlineCommision.getTelephone();
			String numberNom      = commission.getIdentify();
			String identification = airlineCommision.getIdentify();
			
			String airlineInput   = airlineCommision.getNameAirline();
			String adressInput3   = airlineCommision.getAddress2();
			String adressInput4   = airlineCommision.getAddress1();
			String adressInput    = agentCommision.getAddress1();
			String adressInput1   = agentCommision.getAddress2();
			String adressInput2   = agentCommision.getAddress3();
			String nomAgentFormat = agentCommision.getNomAgent();
			
			double money = 0;
			if (!commission.getMoney().isEmpty()) {
				money = Double.valueOf(commission.getMoney());
			} else {
				money = 0;
			}
			
			String adress = String.format("%-35s", adressInput);
			String adress1 = String.format("%-35s", adressInput1);
			String adress2 = String.format("%-35s", adressInput2);
//			String trimmedNumber = numberNom.replaceFirst("^0+", ""); // Remove leading zeros
			String formattedNumber1 = String.format("%12s", numberNom);
//			String formattedNumber2 = String.format("%8s", trimmedNumber);
			String formattedMoney = String.format("%12s", new DecimalFormat("#.###").format(money));
			String nomAgent = String.format("%-27s", nomAgentFormat.substring(0, Math.min(nomAgentFormat.length(), 26)));
			
			String hyphenString0 = new String(new char[78]).replace('\0', '\u002D');
			String hyphenString1 = new String(new char[06]).replace('\0', '\u002D');
			String hyphenString2 = new String(new char[40]).replace('\0', '\u002D');
			String hyphenString3 = new String(new char[37]).replace('\0', '\u002D');
			String hyphenString4 = new String(new char[17]).replace('\0', '\u002D');
			
			document.newPage();
			String formattedNumber0 = String.format("%5d", i);
			
			document.add(new Phrase(String.valueOf(
					"I M P O T S  " + year + " \u002D "
							+ "ETAT DES HONORAIRES,VACATIONS,COURTAGES,COMMISSIONS              D A S   A G E N C E S   (DAS II)\n"
							+ "                             RISTOURNES ET JETONS DE PRESENCE                       (DECLARATION AGENT)      (EX. 2460-"
							+ "1024)\n" + "                      DROITS D’AUTEUR ET D’INVENTEUR PAYES PENDANT L’ANNEE\n" + ""),
					fontText));
			
			document.add(new Phrase(String.valueOf(
					"                                                                                RAISON SOCIALE : "
							+ airlineInput + "\n" + "   LE CADRE CI" + "\u002D"
							+ "APRES EST RESERVE A LA DECLARATION DES SOMMES CI" + "\u002D"
							+ "DESSUS          ADRESSE        :  " + adressInput4 + "\n"
							+ "   VISEES QUI ONT ETE VERSEES A DES PERSONNES N O N  S A L A R I E E S                           "
							+ adressInput3 + "\n"
							+ "                                                                                TELEPHONE      : " + telephone + "\n"
							+ "                                                                                NO POSTE RESPONSABLE :\n"
							+ "                                                                                NO IDENTIFICATION    : "
							+ identification + "\n"),
					fontText));
			
			document.add(new Phrase(String.valueOf(
					"          ********************************************************************************************************\n"
							+ "           NO   I 	    D E S I G N A T I O N     D E S      B E N E F I C I A I R E S           I MONTANT DES     I\n"
							+ "          ORDRE I" + hyphenString0 + "I COMMISSIONS (5) I\n"
							+ "                I      N    O    M  (1)                  I    A D R E S S E    (3)             I RISTOURNES (EUR)I\n"
							+ "          " + hyphenString1 + "I" + hyphenString2 + "I" + hyphenString3 + "I"
							+ hyphenString4 + "I\n"),
					fontText));
			
			for (int j = 0; j < 20; j++) {
				document.add(new Phrase(String.valueOf(
						"          " + formattedNumber0 + " I" + formattedNumber1 + " "
								+ nomAgent + "I " + adress + " I " + formattedMoney + "    I\n"
								+ "               " + " I                                        " + "I " + adress1
								+ " I                " + " I\n" + "               " + " I                                        "
								+ "I " + adress2 + " I                " + " I\n"
								+ "          ******I****************************************I*************************************I*****************I\n"),
						fontText));
				++i;
			}
			
		}
		
		
//			for (String iastNumber : listIastNumber) {
//				document.add(new Phrase(String.valueOf(iastNumber), fontText));
//			}
		
		document.close();
		
		// Set the content type and attachment header
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename="+ airlineCommision.getCodeAirline() + ".DW_DASAIR.Commission.imp.pdf");
		
		// Write the PDF to the response output stream
		OutputStream outputStream = response.getOutputStream();
		baosPDF.writeTo(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
}

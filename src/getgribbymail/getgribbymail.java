package getgribbymail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class getgribbymail {

  final int port = 587;
  final String username = "javamailforgrib@gmail.com";
  final String NOAA = "query@saildocs.com";
  final String password = "javamail29";
  final String host = "pop.gmail.com";
  double lattitudepg=0.0, lattitudepd=0.0, longitudepg=0.0, longitudepd=0.0;

	public void setdonneeenvoyee(double lattitudepg, double lattitudepd, double longitudepg, double longitudepd) {
		setlattitudepg(lattitudepg);
		setlattitudepd(lattitudepd);
		setlongitudepg(longitudepg);
		setlongitudepd(longitudepd);
	}

	private void setlattitudepg(double lattitudepg) {
		this.lattitudepg = lattitudepg;
	}

	private void setlongitudepg(double longitudepg) {
		this.longitudepg = longitudepg;
	}

	private void setlattitudepd(double lattitudepd) {
		this.lattitudepd = lattitudepd;
	}

	private void setlongitudepd(double longitudepd) {
		this.longitudepd = longitudepd;
	}

	public double getlongitudepg() {
		return longitudepg;
	}

	public double getlattitudepg() {
		return lattitudepg;
	}

	public double getlattitudepd() {
		return lattitudepd;
	}

	public double getlongitudepd() {
		return longitudepd;
	}

	private void envoiMessage() {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(NOAA));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(NOAA));
			message.setSubject("Teste");
			message.setText("Send grib:" + getlattitudepg() + "S," + getlattitudepd() + "S," + getlongitudepg() + "W,"
					+ getlongitudepd() + "W|1,1|00,12,24|WIND");

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", port, username, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	private Message RecupMessage() {

		Message monmessage=null;
		
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, username, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("inbox");
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array
			Message[] messages = emailFolder.getMessages();
			monmessage=messages[messages.length - 1];


		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monmessage;
	}

	private String recuperationfichiergribenpiecejointe(Message message)
			throws FileNotFoundException, MessagingException, IOException {

		String disposition = "";
		String nomfichier = "";

		Multipart mp = (Multipart) message.getContent();
		double n = mp.getCount();
		for (int j = 0; j < n; j++) {
			Part part = mp.getBodyPart(j);
			disposition = part.getDisposition();
			if ((disposition != null) && ((disposition.equalsIgnoreCase(Part.ATTACHMENT)
					|| (disposition.equalsIgnoreCase(Part.INLINE))))) {
				File save = new File(part.getFileName());
				nomfichier = part.getFileName();
				FileWriter ecriture = new FileWriter(save);
				InputStreamReader lecture = new InputStreamReader(part.getInputStream());
				int flux;
				while ((flux = lecture.read()) != 0) {
					ecriture.write(flux);
				}
				lecture.close();
				ecriture.close();
			}
		}

		return nomfichier;
	}
	
	public String recuperationfichiergrib(double lattitudepg, double lattitudepd, double longitudepg, double longitudepd) throws FileNotFoundException, MessagingException, IOException, InterruptedException {
		
		
		setdonneeenvoyee(lattitudepg, lattitudepd, longitudepg, longitudepd);
		
		Message monmessage=null;
		
		envoiMessage();
		
		Thread.sleep(4000);
		
		monmessage=RecupMessage();
		
		String lienversfichier="";
		
		lienversfichier = recuperationfichiergribenpiecejointe(monmessage);
		
		return lienversfichier;
		
		
	}
	
	
}
package ch.heigvd.smtp_lab.smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import ch.heigvd.smtp_lab.model.Mail;
import ch.heigvd.smtp_lab.model.Person;

public class SmtpClient {

	private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

	private String serverAddress;
	private int serverPort;

	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;

	public SmtpClient(String serverAddress, int serverPort) {
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}

	public void sendMail(Mail mail) throws IOException {
		socket = new Socket(serverAddress, serverPort);
		writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String line;
		// Read welcome message
		line = reader.readLine();
		LOG.info(line);

		// EHLO message
		writer.write("EHLO prank\r\n");
		writer.flush();
		for (int i = 0; i < 3; i++) {
			line = reader.readLine();
			checkLine(line);
		}

		// Set the sender
		writer.write("MAIL FROM:" + mail.getSender().getEmail() + "\r\n");
		writer.flush();

		// Read OK message
		line = reader.readLine();
		checkLine(line);

		// Set the receivers
		for (Person p : mail.getReceivers()) {
			writer.write("RCPT TO:" + p.getEmail() + "\r\n");
			writer.flush();

			// Read OK message
			line = reader.readLine();
			checkLine(line);
		}

		// Send DATA
		writer.write("DATA \r\n");
		writer.flush();

		line = reader.readLine();
		LOG.info(line);
		
		for (Person p : mail.getReceivers()) {
			writer.write("bcc:" + p.getEmail() + "\r\n");
		}
		writer.flush();

		// Set subject
		writer.write("Subject: " + mail.getSubject() + "\r\n\r\n");

		// Content
		writer.write(mail.getContent() + "\r\n.\r\n");
		writer.flush();

		line = reader.readLine();
		checkLine(line);

		reader.close();
		writer.close();
	}

	private void checkLine(String line) throws IOException {
		LOG.info(line);

		if (!line.startsWith("250")) {
			throw new IOException("SMTP error : " + line);
		}
	}
}

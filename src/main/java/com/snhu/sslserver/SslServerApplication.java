
//by malcolm mcdonough
package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";
@RestController 
class SslServerController {
	
	@RequestMapping("/hash")
	public String myHash() throws NoSuchAlgorithmException {

		String data = "Hello World Check Sum!";
		String name = "Malcolm McDonough";

		String[ ] splitname = name.split(" ");
		String firstname = splitname [0];
		String lastname = splitname[splitname.length - 1];
		

		if (splitname.length > 1) {
			// If there is more than one name, concatenate the first and last name
			name = firstname + " " + lastname;
		}
		

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] shavalue = md.digest(name.getBytes(StandardCharsets.UTF_8));
		

		StringBuilder response = new StringBuilder();
		response.append("Data: ").append(data).append("</br></br>");
		response.append("Name: ").append(name).append("</br></br>");
		response.append("Name of algorithm used: SHA-256 CheckSum Value: ").append(bytesToHex(shavalue));
		

		return response.toString();
	}
	

	public String bytesToHex(byte[] sha256) {
		BigInteger hex = new BigInteger(1, sha256);
		StringBuilder checksum = new StringBuilder(hex.toString(16));
		
		while (checksum.length() < 32) {
			checksum.insert(0, '0');
		}
		
		return checksum.toString();
	}
}

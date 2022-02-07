package message;

import java.io.Serializable;
import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import message.Message.TypeMessage;

public abstract class Message implements Serializable {

	public enum TypeMessage {JE_SUIS_CONNECTE, JE_SUIS_DECONNECTE, INFO_PSEUDO, TEXTE, IMAGE, FICHIER, MESSAGE_NUL}
	protected TypeMessage type;
	private static final long serialVersionUID = 1L;
	private static Instrumentation inst;

	public TypeMessage getTypeMessage() {
		return this.type;
	}

	protected abstract String attributsToString();

	public String toString() {
		return this.type+"###"+this.attributsToString();
	}	

	public static Message stringToMessage(String messageString) {
		try {
			String[] parts = messageString.split("###");
			switch (parts[0]) {
			case "JE_SUIS_CONNECTE" :
				return new MessageSysteme(TypeMessage.JE_SUIS_CONNECTE);
				
			case "JE_SUIS_DECONNECTE" :
				return new MessageSysteme(TypeMessage.JE_SUIS_DECONNECTE);
				
			case "INFO_PSEUDO" :
				return new MessageSysteme(TypeMessage.INFO_PSEUDO, parts[1], parts[2]);
				
			case "TEXTE" :
				return new MessageText(TypeMessage.TEXTE, parts[1]);
				
			case "IMAGE" :
				return new MessageFichier(TypeMessage.IMAGE, parts[1], parts[2]);
				
			case "FICHIER" :
				return new MessageFichier(TypeMessage.FICHIER, parts[1], parts[2]);
			}
		} catch (MauvaisTypeMessageException e) {}
			return null;
	}

	//tests ici
	public static void main(String[] args) throws MauvaisTypeMessageException {		
		Message m1 = new MessageSysteme(TypeMessage.JE_SUIS_CONNECTE);
		Message m2 = new MessageSysteme(TypeMessage.JE_SUIS_DECONNECTE);
		Message m3 = new MessageSysteme(TypeMessage.INFO_PSEUDO, "pseudo156434518", "id236");
		Message m4 = new MessageText(TypeMessage.TEXTE, "blablabla");
		Message m5 = new MessageFichier(TypeMessage.FICHIER, "bidon", ".pdf");
		
		
		System.out.println(Message.stringToMessage(m1.toString()));
		System.out.println(Message.stringToMessage(m2.toString()));
		System.out.println(Message.stringToMessage(m3.toString()));
		System.out.println(Message.stringToMessage(m4.toString()));
		System.out.println(Message.stringToMessage(m5.toString()));
		
	}

}

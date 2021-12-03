package message;

public class MessageSysteme extends Message {
	
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private String id;

	public MessageSysteme(TypeMessage type) throws MauvaisTypeMessageException{
		if ((type==TypeMessage.JE_SUIS_CONNECTE)||(type==TypeMessage.JE_SUIS_DECONNECTE)||(type==TypeMessage.MESSAGE_NUL)) {
			this.type=type;
			this.pseudo="";
			this.id="";
		}
		else throw new MauvaisTypeMessageException();
	}
	
	public MessageSysteme(TypeMessage type, String pseudo, String id) throws MauvaisTypeMessageException {
		if (type==TypeMessage.INFO_PSEUDO) {
			this.type=type;
			this.pseudo=pseudo;
			this.id=id;
		}
		else throw new MauvaisTypeMessageException();
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public String getId() {
		return this.id;
	}

	@Override
	protected String attributsToString() {
		return this.pseudo+"###"+this.id;
	}
}

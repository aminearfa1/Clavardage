package message;

import message.Message.TypeMessage;

public class MessageFichier extends Message {
	

	private static final long serialVersionUID = 1L;
	private String contenu;
	private String extension;

	public MessageFichier(TypeMessage type, String contenu, String extension) throws MauvaisTypeMessageException{
		if ((type==TypeMessage.IMAGE)||(type==TypeMessage.FICHIER)) {
			this.type=type;
			this.contenu=contenu;
			this.extension=extension;
		}
		else throw new MauvaisTypeMessageException();
	}

	public String getContenu() {
		return this.contenu;
	}
	
	public String getExtension() {
		return this.extension;
	}

	@Override
	protected String attributsToString() {
		return this.contenu+"###"+this.extension;
	}
}
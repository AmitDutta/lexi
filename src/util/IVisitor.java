package util;

import model.*;

public abstract class IVisitor{
	
	public abstract void visitChar(Char ch);
	public abstract void visitPicture(Picture picture);
	public abstract void visitRow(Row row);
}

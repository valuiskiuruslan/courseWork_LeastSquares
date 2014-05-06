package ru.valuev.gui.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class DataFilter extends FileFilter
{
	@Override
	public boolean accept(File pathname)
	{
		if (pathname.isDirectory())
			return true;

		String extension = ru.valuev.gui.io.Utils.getExtension(pathname);
		if (extension != null)
		{
			if (extension.equals(Utils.Ext.DAT.certainValue))
				return true;
			 else					
				return false;
		}

		return false;
	}

	@Override
	public String getDescription()
	{
		return "Your data";
	}

}

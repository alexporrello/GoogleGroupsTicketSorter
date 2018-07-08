package reader;

import java.util.ArrayList;
import java.util.HashMap;

import reader.FileCabinet.Drawer;
import reader.FileCabinet.Drawer.Folder;


public class FileCabinet extends HashMap<String, Drawer>{
	private static final long serialVersionUID = -7237604673224196120L;
	
	public FileCabinet() {
		put(Drawer.HYRAX, new Drawer());
		put(Drawer.BES, new Drawer());
		put(Drawer.OLFS, new Drawer());
	}
	
	public class Drawer extends HashMap<String, Folder> {
		private static final long serialVersionUID = -947735196735807759L;

		public static final String HYRAX = "Hyrax";
		public static final String BES   = "BES";
		public static final String OLFS  = "OLFS";
		
		
		
		public class Folder extends ArrayList<SupportPage> {
			private static final long serialVersionUID = -4049054814794712786L;

			
		}
	}
}

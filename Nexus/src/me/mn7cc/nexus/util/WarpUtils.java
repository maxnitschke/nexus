package me.mn7cc.nexus.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.mn7cc.nexus.Database;

public class WarpUtils {

	public static List<String> getWarpList(int page) {

		List<String> list = new ArrayList<String>();
	    Iterator<String> iterator = Database.getWarpIdSet().iterator();
	    for(int i = 0; i < page * 50; i++) if(!iterator.hasNext()) return list; iterator.next();
	    for(int i = 0; i < 50; i++) if(!iterator.hasNext()) return list; list.add(iterator.next());
		return list;
		
	}
	
}

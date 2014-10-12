package celestibytes.ctie.item;

import java.util.ArrayList;
import java.util.List;

public class ItemType {
	
	private static List<ItemType> itemTypes = new ArrayList<ItemType>();

	private int typeId = -1;
	private String typeName;
	private List<String> subtypes = new ArrayList<String>();
	
	public ItemType(String typeName) {
		
	}
	
	public void addSubType(String subtype) {
		subtype = subtype.toUpperCase();
		if(!subtypes.contains(subtype)) {
			subtypes.add(subtype);
		} else {
			System.err.println("Subtype already added, Ignoring... (Nothing to worry about)");
		}
		
	}
	
	public static int registerItemType(ItemType itype) {
		if(!itemTypes.contains(itype)) {
			
		} else {
			// ----
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof ItemType && ((ItemType)o).typeName.equals(this.typeName);
	}
}

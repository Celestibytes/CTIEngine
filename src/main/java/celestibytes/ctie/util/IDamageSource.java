package celestibytes.ctie.util;

public interface IDamageSource {
	
	/**@param killerName Note: can be null (example: died due to gravity)*/
	public String getDeathMessage(String killedName, String killerName);
	
	/**@return relative to 1*/
	public float getRegularArmorPenetration();
	
	/**@return relative to 1*/
	public float getMagicArmorPenetration();
	
}

package abilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import methods_plugins.AmonPackPlugin;
import methods_plugins.Methods;
import methods_plugins.SmokeAbility;



public class SmokeSurge extends SmokeAbility implements AddonAbility {
	private int Cooldown = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.Cooldown");
	private int dmg = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.Dmg");
	private int range = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.Range");
	private int slowpower = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.SlowPower");
	private int slowdur = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.SlowDuration");
	private int poisonpower = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.PoisonPower");
	private int poisondur = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.PoisonDuration");
	private int blinddur = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.BlindnessDuration");
	private int zonerange = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.SmokeZoneRange");
	private int zonedur = AmonPackPlugin.plugin.getConfig().getInt("AmonPack.Fire.Smoke.SmokeSurge.SmokeZoneDuration");
	public int i;
	public int act;
	Location origin;
	Location location;
	Vector direction;
	public int degree;
	public int degree2;
	public SmokeSurge(Player player) {
		super(player);
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		if (!bPlayer.canBend(this)) {
			return;
		}
		i=0;
		degree = -180;
		degree2 = -60;
		origin = player.getLocation().clone();
		location = origin.clone().add(0,1,0);
		direction = player.getLocation().getDirection().clone();
		start();
		bPlayer.addCooldown(this);
	        
	}
	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		if (GeneralMethods.isSolid(location.getBlock())) {
			Methods.CreateSmokeZone(player,location.clone(), this, zonerange, zonedur);
			remove();
			return;
		}
		if (origin.distance(location) > (range)) {
			Methods.CreateSmokeZone(player,location.clone(), this, zonerange, zonedur);
			remove();
			return;
		}
		
		for (Block b : GeneralMethods.getBlocksAroundPoint(location, 3)) {
		if (b.getType() == Material.FIRE) {
			Methods.CreateSmokeZone(player,location.clone(), this, zonerange, zonedur);
		}
		}
		
		location.add(direction.multiply(1));
		vortex();
   		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2)) {
		if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
		DamageHandler.damageEntity(entity, dmg, this);
			((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, slowdur, slowpower));
			((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.POISON, poisondur, poisonpower));
			((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, blinddur, 1));
    	}}
	}
	public void vortex() {
		if (degree < 180) {
        degree = degree + 30;
		  	Location loc = player.getLocation();
		  	loc.setYaw(loc.getYaw() + 90);
        Location tempLoc = location.clone();
        Vector newDir = loc.getDirection().clone().multiply(2 * Math.cos(Math.toRadians(degree)));
        tempLoc.add(newDir);
        tempLoc.setY(tempLoc.getY() + 2 * Math.sin(Math.toRadians(degree)));
        ParticleEffect.SMOKE_NORMAL.display(tempLoc, 15, 0.6, 0.6, 0.6, 0);
    } else {
        degree = -180;
    }
		if (degree2 > -180) {
	        degree2 = degree2 - 30;
			  	Location loc2 = player.getLocation();
			  	loc2.setYaw(loc2.getYaw() + 90);
	        Location tempLoc2 = location.clone();
	        Vector newDir2 = loc2.getDirection().clone().multiply(2 * Math.cos(Math.toRadians(degree2)));
	        tempLoc2.add(newDir2);
	        tempLoc2.setY(tempLoc2.getY() + 2 * Math.sin(Math.toRadians(degree2)));
	        ParticleEffect.SMOKE_NORMAL.display(tempLoc2, 15, 0.6, 0.6, 0.6, 0);
	             
	} else {
	        degree2 = 180;
	}
		}
	
	@Override
	public long getCooldown() {
		return Cooldown;
	}
	@Override
	public Location getLocation() {
		return null;
	}
	@Override
	public String getName() {
		return "SmokeSurge";
	}
	@Override
	public String getDescription() {
		return "";
	}
	@Override
	public String getInstructions() {
		return "";
	}
	@Override
	public String getAuthor() {
		return "AmonPack";
	}
	@Override
	public String getVersion() {
		return "1.0";
	}
	@Override
	public boolean isHarmlessAbility() {
		return false;
	}
	@Override
	public boolean isSneakAbility() {
		return false;
	}
	@Override
	public void load() {}
	@Override
	public void stop() {
		super.remove();
	}
}
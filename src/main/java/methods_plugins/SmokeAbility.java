package methods_plugins;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.ability.SubAbility;

import org.bukkit.entity.Player;

public abstract class SmokeAbility extends FireAbility implements SubAbility {
    public SmokeAbility(Player player) {
        super(player);
    }

    public Class<? extends Ability> getParentAbility() {
        return FireAbility.class;
    }

    public Element getElement() {
        return AmonPackPlugin.getSmokeElement();
    }
}

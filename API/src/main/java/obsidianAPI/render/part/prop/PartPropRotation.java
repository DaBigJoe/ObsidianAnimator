package obsidianAPI.render.part.prop;

import obsidianAPI.render.ModelObj;
import obsidianAPI.render.part.PartRotation;

public class PartPropRotation extends PartRotation 
{

	public PartPropRotation(ModelObj mObj) 
	{
		super(mObj, "prop_rot");
	}

	public PartPropRotation(ModelObj modelObj, String name)
	{
		super(modelObj, name);
	}
}

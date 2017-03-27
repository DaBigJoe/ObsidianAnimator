package obsidianAPI.animation;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import obsidianAPI.Quaternion;
import obsidianAPI.Util;
import obsidianAPI.render.part.Part;
import obsidianAPI.render.part.PartObj;
import obsidianAPI.render.part.PartRotation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * A section of an animation for a specific part. 
 */
public class AnimationPart 
{
	private int startTime;
	private int endTime;
	private float[] startPosition;
	private float[] endPosition;
	private float[] movement = new float[3];
	private String partName;
	private DecimalFormat df = new DecimalFormat("##.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	private Quaternion startQuart, endQuart;

	public AnimationPart(NBTTagCompound compound) 
	{
		loadData(compound);
	}

	public AnimationPart(int startTime, int endTime, float[] startPosition, float[] endPosition, Part part)
	{
		this.startTime = startTime;
		this.endTime = endTime;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.partName = part.getName();
		init();
	}
	
	private void init()
	{
		this.startQuart = Quaternion.fromEuler(startPosition[0], startPosition[1], startPosition[2]);
		this.endQuart = Quaternion.fromEuler(endPosition[0], endPosition[1], endPosition[2]);

		for(int i = 0; i < 3; i++)
		{
			float dT = endTime - startTime;
			//This only happens when there is only one keyframe for a part and
			//it is at time zero.
			if(dT == 0)
				dT = 1;
			float dif = endPosition[i] - startPosition[i];
			this.movement[i] = dif/dT;
		}
	}

	public void animatePart(Part part, float time) 
	{
		float[] values = new float[3];
		if(part instanceof PartRotation)
		{
			float t = time/(endTime - startTime);

			Quaternion interpolatedQ = Quaternion.slerp(startQuart,endQuart,t);
			values = interpolatedQ.toEuler();
		}
		else
			values = getPartRotationAtTime(time);

		if (!partName.equals("entitypos"))
		{
			for (int i = 0; i < 3; i++)
			{
				if (values[i] < -Math.PI)
					values[i] += 2 * Math.PI;
				else if (values[i] > Math.PI)
					values[i] -= 2 * Math.PI;
			}
		}
		
		part.setValues(values);
	}
	
	public float[] getPartRotationAtTime(float time)
	{
		float[] values = new float[3];
		for(int i = 0; i < 3; i++)
		{
			values[i] = startPosition[i] + time*movement[i];
			if(values[i] < -Math.PI)
				values[i] += 2*Math.PI;
			else if(values[i] > Math.PI)
				values[i] -= 2*Math.PI;	
		}
		return values;
	}

	public float[] getStartPosition() 
	{
		return startPosition;
	}

	public float[] getEndPosition() 
	{
		return endPosition;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public int getEndTime()
	{
		return endTime;
	}

	/**
	 * Return true if the float array has the same values as the startPosition
	 */
	public boolean isStartPos(float[] rotation) 
	{
		for(int i = 0; i < 3; i++)
		{
			if(rotation[i] != startPosition[i])
				return false;
		}
		return true;
	}

	public boolean isEndPosDifferentToStartPos()
	{
		for(int i = 0; i < 3; i++)
		{
			if(startPosition[i] != endPosition[i])
				return true;
		}
		return false;
	}
	
	public String getPartName() 
	{
		return partName;
	}

	public NBTBase getSaveData() 
	{
		NBTTagCompound animationData = new NBTTagCompound();		
		animationData.setFloat("XStart", Float.parseFloat(df.format(this.startPosition[0])));
		animationData.setFloat("YStart", Float.parseFloat(df.format(this.startPosition[1])));
		animationData.setFloat("ZStart", Float.parseFloat(df.format(this.startPosition[2])));
		animationData.setFloat("XEnd", Float.parseFloat(df.format(this.endPosition[0])));
		animationData.setFloat("YEnd", Float.parseFloat(df.format(this.endPosition[1])));
		animationData.setFloat("ZEnd", Float.parseFloat(df.format(this.endPosition[2])));
		animationData.setFloat("StartTime", this.startTime);
		animationData.setFloat("FinishTime", this.endTime);
		animationData.setString("Part", partName);
		return animationData;
	}

	public void loadData(NBTTagCompound compound) 
	{
		this.startPosition = new float[3];
		this.endPosition = new float[3];
		this.startPosition[0] = compound.getFloat("XStart");
		this.startPosition[1] = compound.getFloat("YStart");
		this.startPosition[2] = compound.getFloat("ZStart");
		this.endPosition[0] = compound.getFloat("XEnd");
		this.endPosition[1] = compound.getFloat("YEnd");
		this.endPosition[2] = compound.getFloat("ZEnd");
		this.startTime = compound.getInteger("StartTime");
		this.endTime = compound.getInteger("FinishTime");
		this.partName = compound.getString("Part");
		init();
	}


}

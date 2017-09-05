package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class cilpRotationXZTask extends WeSimpleTask {

	public cilpRotationXZTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
		int[][][] res=new int[size.z][size.x][size.y];
		int[] b=null;
		
		for(int x=0;x<size.x;x++){
			for(int z=0;z<size.z;z++){
				b=res[z][x];
				res[z][x]=blocks[x][z];
				blocks[x][z]=b;
			}
		}
		cilp.setContent(res);
		return true;
	}

}

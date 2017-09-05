package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class clipRotationXTask extends WeSimpleTask {

	public clipRotationXTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
//		int[][][] resb=new int[size.x][size.z][size.y];
		int xp=size.x/2;
		int[][] b=null;
		
		for(int x=0;x<xp;x++){
			b=blocks[size.x-x-1];
			blocks[size.x-x-1]=blocks[x];
			blocks[x]=b;
		}
		
		cilp.setContent(blocks);
		return true;
	}
	
}

package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class clipRotationYTask extends WeSimpleTask {

	public clipRotationYTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
//		int[][][] resb=new int[size.x][size.z][size.y];
		int yp=size.y/2;
		int b=0;
		
		for(int x=0;x<size.x;x++){
			for(int z=0;z<size.z;z++){
				for(int y=0;y<yp;y++){
					b=blocks[x][z][size.y-y-1];
					blocks[x][z][size.y-y-1]=blocks[x][z][y];
					blocks[x][z][y]=b;
				}
			}
		}
		
		cilp.setContent(blocks);
		return true;
	}

}

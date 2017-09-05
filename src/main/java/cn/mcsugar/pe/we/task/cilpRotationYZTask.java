package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class cilpRotationYZTask extends WeSimpleTask {

	public cilpRotationYZTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
		int[][][] res=new int[size.x][size.y][size.z];
		int b=0;
		
		for(int x=0;x<size.x;x++){
			for(int z=0;z<size.z;z++){
				for(int y=0;y<size.y;y++){
					b=res[x][y][z];
					res[x][y][z]=blocks[x][z][y];
					blocks[x][z][y]=b;
				}
			}
		}
		
		cilp.setContent(res);
		return true;
	}

}

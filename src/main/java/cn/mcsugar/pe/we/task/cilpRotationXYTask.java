package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeSimpleTask;
import cn.nukkit.math.BlockVector3;

public class cilpRotationXYTask extends WeSimpleTask {

	public cilpRotationXYTask(byte m) {
		super(m);
	}

	@Override
	protected boolean processEdit(ClipboardStore cilp) {
		BlockVector3 size=cilp.getSize();
		int[][][] blocks=cilp.getContent();
		
		int[][][] res=new int[size.y][size.z][size.x];
		int b=0;
		
		for(int x=0;x<size.x;x++){
			for(int z=0;z<size.z;z++){
				for(int y=0;y<size.y;y++){
					b=res[y][z][x];
					res[y][z][x]=blocks[x][z][y];
					blocks[x][z][y]=b;
				}
			}
		}
		
		cilp.setContent(res);
		return true;
	}

}

package cn.mcsugar.pe.we.task;

import cn.nukkit.level.Level;
import cn.nukkit.level.SimpleChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import java.util.ArrayList;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeTask;

public class clipReadTask extends WeTask{

public int sx,sy,sz,ex,ey,ez;
public BaseFullChunk[] chunks=null;

public clipReadTask(Level level,byte m,int sx,int sy,int sz,int ex,int ey,int ez){
this.sx=sx;
this.sy=sy;
this.sz=sz;
this.ex=ex;
this.ey=ey;
this.ez=ez;
this.m=m;
int count=0;
ArrayList<BaseFullChunk> listbuf=new ArrayList<BaseFullChunk>();
for(int x=sx>>4;x<=ex>>4;x++){
for(int z=sz>>4;z<=ez>>4;z++){
BaseFullChunk chunk=level.getChunk(x,z).clone();
if(chunk==null){
return;
}
listbuf.add(count,chunk.clone());
count++;
}
}
this.chunks=new BaseFullChunk[count];
for(int i=0;i<count;i++){
this.chunks[i]=listbuf.get(i);
}
}

@Override
public void onRun(){
if(this.chunks==null){
return;
}
ClipboardStore cilp=ClipboardStore.get(this.m);
if(cilp.isWorking()){
return;
}
SimpleChunkManager level=new SimpleChunkManager(0);
for(BaseFullChunk chunk:this.chunks){
level.setChunk(chunk.getX(),chunk.getZ(),chunk);
}
int bx=this.ex-this.sx+1;
int by=this.ey-this.sy+1;
int bz=this.ez-this.sz+1;
int[][][] parsed=new int[bx][bz][by];
for(int x=this.sx;x<=this.ex;x++){
for(int y=this.sy;y<=this.ey;y++){
for(int z=this.sz;z<=this.ez;z++){
parsed[x-this.sx][z-this.sz][y-this.sy]=(level.getBlockIdAt(x,y,z)<<16)|(level.getBlockDataAt(x,y,z)&0xffff);
}
}
}
if(cilp.isWorking()){
return;
}
cilp.setContent(parsed);
this.result=true;
}

}
package cn.mcsugar.pe.we.task;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.SimpleChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.BlockVector3;
import java.util.ArrayList;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeTask;

public class clipWriteTask extends WeTask{

public clipWriteTask(Level level,byte m,int sx,int sy,int sz){
this.m=m;
this.x=sx;
this.y=sy;
this.z=sz;
BlockVector3 cilpSize=ClipboardStore.get(m).getSize();
int ex=sx+cilpSize.x;
int ez=sz+cilpSize.z;
this.lid=level.getId();
int count=0;
ArrayList<BaseFullChunk> listbuf=new ArrayList<BaseFullChunk>();
for(int x=sx>>4;x<=ex>>4;x++){
for(int z=sz>>4;z<=ez>>4;z++){
BaseFullChunk chunk=level.getChunk(x,z);
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

public int x,y,z;
public BaseFullChunk[] chunks=null;
private int lid;

@Override
public void onRun(){
if(this.chunks==null){
return;
}
SimpleChunkManager level=new SimpleChunkManager(0);
for(BaseFullChunk chunk:this.chunks){
level.setChunk(chunk.getX(),chunk.getZ(),chunk);
}
ClipboardStore cilp=ClipboardStore.get(this.m);
BlockVector3 cilpSize=cilp.getSize();
int[][][] bs=cilp.getContent();
for(int x=0;x<cilpSize.x;x++){
for(int y=0;y<cilpSize.y;y++){
for(int z=0;z<cilpSize.z;z++){
int b=bs[x][z][y];
level.setBlockIdAt(this.x+x,this.y+y,this.z+z,b>>16);
level.setBlockDataAt(this.x+x,this.y+y,this.z+z,b&0xffff);
}
}
}
this.result=true;
}

@Override
public void onCompletion(Server server) {
if(this.chunks==null){
this.result=false;
super.onCompletion(server);
return;
}
if(!this.result){
super.onCompletion(server);
return;
}
boolean success=false;
try{
Level level=server.getLevel(this.lid);
for(BaseFullChunk chunk:this.chunks){
level.setChunk(chunk.getX(),chunk.getZ(),chunk);
}
success=true;
}finally{
if(!success){
this.result=false;
}
super.onCompletion(server);
}
}

}
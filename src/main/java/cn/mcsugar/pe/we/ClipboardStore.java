package cn.mcsugar.pe.we;

import cn.nukkit.math.BlockVector3;

public class ClipboardStore{

private static ClipboardStore[] MapList=new ClipboardStore[32];

public static ClipboardStore get(byte id,boolean create){
id&=0x20;
ClipboardStore u=ClipboardStore.MapList[id];
if(u==null&&create){
u=new ClipboardStore(id);
synchronized(ClipboardStore.MapList){
ClipboardStore.MapList[id]=u;
}
}
return u;
}

public final byte id;
private BlockVector3 size;
private int[][][] content;
/* Clip FullBlock[x][z][y] */
private boolean working=false;

public byte getId(){
return this.id;
}

public boolean isWorking(){
return this.working;
}

public ClipboardStore(byte id){
this.id=id;
this.size=new BlockVector3();
this.setContent(new int[1][1][1]);
}

public BlockVector3 getSize(){
return this.size.clone();
}

public int[][][] getContent(){
return this.content.clone();
}

public synchronized void setContent(int[][][] clip){
this.working=true;
this.size.setComponents(clip.length,clip[0][0].length,clip[0].length);
this.content=clip;
this.working=false;
}

public int countSize(){
return this.size.x*this.size.y*this.size.z;
}

public synchronized void replace(int oldB,int newB){
this.working=true;
for(int x=0;x<this.size.x;x++){
for(int y=0;y<this.size.y;y++){
for(int z=0;z<this.size.z;z++){
if(this.content[x][z][y]==oldB){
this.content[x][z][y]=newB;
}
}
}
}
this.working=false;
}

public synchronized void setBlocks(int b){
this.working=true;
this.content=new int[this.size.x][this.size.z][this.size.y];
if(b!=0){
for(int x=0;x<this.size.x;x++){
for(int y=0;y<this.size.y;y++){
for(int z=0;z<this.size.z;z++){
this.content[x][z][y]=b;
}
}
}
}
this.working=false;
}

public int getBlock(int x,int y,int z){
return this.content[x][z][y];
}

public synchronized void setBlock(int x,int y,int z,int b){
this.working=true;
this.content[x][z][y]=b;
this.working=false;
}

public static ClipboardStore get(byte m) {
	return ClipboardStore.get(m,true);
}

}
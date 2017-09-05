package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeTask;

public class clipReplaceTask extends WeTask{

public clipReplaceTask(byte m,int oldb,int newb){
this.m=m;
this.oldb=oldb;
this.newb=newb;
}

private int oldb,newb;

@Override
public void onRun(){
ClipboardStore cilp=ClipboardStore.get(this.m);
if(cilp.isWorking()){
return;
}
cilp.replace(this.oldb,this.newb);
this.result=true;
}

}
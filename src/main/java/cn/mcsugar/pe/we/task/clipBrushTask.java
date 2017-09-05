package cn.mcsugar.pe.we.task;

import cn.mcsugar.pe.we.ClipboardStore;
import cn.mcsugar.pe.we.WeTask;

public class clipBrushTask extends WeTask{

private int b;

public clipBrushTask(byte m,int b){
this.m=m;
this.b=b;
}

@Override
public void onRun(){
ClipboardStore cilp=ClipboardStore.get(this.m);
if(cilp.isWorking()){
return;
}
cilp.setBlocks(this.b);
this.result=true;
}

}
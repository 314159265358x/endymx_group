package cn.mcsugar.pe.we;

public abstract class WeSimpleTask extends WeTask {
	protected byte m;
	
	public WeSimpleTask(byte m){
		this.m=m;
	}
	
	@Override
	public void onRun(){
		ClipboardStore clip=ClipboardStore.get(this.m);
		if(clip.isWorking()){
			this.result=false;
			return;
		}
		this.result=this.processEdit(clip);
	}
	
	protected abstract boolean processEdit(ClipboardStore cilp); 

}

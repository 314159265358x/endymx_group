package cn.mcsugar.pe.we;

import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.network.protocol.TextPacket;

import java.util.Collection;

import cn.nukkit.Player;
import cn.nukkit.Server;

public abstract class WeTask extends AsyncTask{

public static final TextPacket msgT=new TextPacket();
public static final TextPacket msgF=new TextPacket();
protected byte m;
protected boolean result=false;

static{
TextPacket vt=WeTask.msgT;
vt.message="操作完成";
vt.type=TextPacket.TYPE_RAW;
TextPacket vf=WeTask.msgF;
vt.message="操作完成";
vf.type=TextPacket.TYPE_RAW;
}

public void onCompletion(Server server) {
Collection<Player> p=server.getLevelByName("m"+this.m).getPlayers().values();
if(this.result){
Server.broadcastPacket(p,WeTask.msgT);
}else{
Server.broadcastPacket(p,WeTask.msgF);
}
}

}
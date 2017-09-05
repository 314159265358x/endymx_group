package cn.mcsugar.pe.we;

import cn.mcsugar.pe.group.Main;
import cn.mcsugar.pe.we.task.cilpRotationXYTask;
import cn.mcsugar.pe.we.task.cilpRotationXZTask;
import cn.mcsugar.pe.we.task.cilpRotationYZTask;
import cn.mcsugar.pe.we.task.clipBrushTask;
import cn.mcsugar.pe.we.task.clipReadTask;
import cn.mcsugar.pe.we.task.clipReplaceTask;
import cn.mcsugar.pe.we.task.clipRotationXTask;
import cn.mcsugar.pe.we.task.clipRotationYTask;
import cn.mcsugar.pe.we.task.clipRotationZTask;
import cn.mcsugar.pe.we.task.clipWriteTask;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockVector3;

public class WeCmd extends Command{

private Main plugin;
private Server server;

public WeCmd(Main plugin){
		super("we", "简易地图编辑器","/we <string:args[0]>", new String[0]);
this.plugin=plugin;
this.server=plugin.getServer();
}
@Override
public boolean execute(CommandSender sender, String label, String[] args) {

if(args.length<1){
args=new String[]{"help"};
}
if(!(sender instanceof Player)){
	return true;
}
Player p=(Player) sender;
byte m=Main.getInstance().getWorkLevel(p);
if(ClipboardStore.get(m).isWorking()){
	p.sendMessage("系统繁忙，请稍后重试");
	return true;
}
Level level=this.server.getLevelByName("m"+m);
WeTask task;
switch(args[0]){
case "copy":
if(args.length<7){
p.sendMessage("参数不全，请查看帮助");
break;
}
int x1=0;
int y1=0;
int z1=0;
int x2=0;
int y2=0;
int z2=0;
try{
x1=Integer.parseInt(args[1]);
y1=Integer.parseInt(args[2]);
z1=Integer.parseInt(args[3]);
x2=Integer.parseInt(args[4]);
y2=Integer.parseInt(args[5]);
z2=Integer.parseInt(args[6]);
}catch(NumberFormatException e){
	p.sendMessage("参数格式错误，请查看帮助");
	return true;
}
int sx=Math.min(x1, x2);
int sy=Math.min(y1, y2);
int sz=Math.min(z1, z2);
int ex=Math.max(x1, x2);
int ey=Math.max(y1, y2);
int ez=Math.max(z1, z2);
if(ClipboardStore.get(m).isWorking()){
	p.sendMessage("系统繁忙，请稍后重试");
	return true;
}
int count=(ex-sx)*(ey-sy)*(ez-sz);
if(count>0x10000){
	p.sendMessage("体积太大，请重新选择");
	return true;
}
task=new clipReadTask(level,m,sx,sy,sz,ex,ey,ez);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "parse":
if(args.length<4){
p.sendMessage("参数不全，请查看帮助");
break;
}
int x=0;
int y=0;
int z=0;
try{
x=Integer.parseInt(args[1]);
y=Integer.parseInt(args[2]);
z=Integer.parseInt(args[3]);
}catch(NumberFormatException e){
p.sendMessage("参数格式错误，请查看帮助");
return true;
}
if(ClipboardStore.get(m).isWorking()){
	p.sendMessage("系统繁忙，请稍后重试");
	return true;
}
task=new clipWriteTask(level,m,x,y,z);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "set":
case "brush":
if(args.length<3){
p.sendMessage("参数不全，请查看帮助");
break;
}
int id;
int data;
try{
id=Integer.parseInt(args[1])&0xff;
data=Integer.parseInt(args[2])&0xffff;
}catch(NumberFormatException e){
	p.sendMessage("参数格式错误，请查看帮助");
	return true;
}
task=new clipBrushTask(m,(((id&0xff)<<16)|(data&0xffff)));
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "replace":
if(args.length<5){
p.sendMessage("参数不全，请查看帮助");
break;
}
int oldbi=0;
int oldbd=0;
int newbi=0;
int newbd=0;
try{
oldbi=Integer.parseInt(args[1])&0xff;
oldbd=Integer.parseInt(args[2])&0xffff;
newbi=Integer.parseInt(args[3])&0xff;
newbd=Integer.parseInt(args[4])&0xffff;
}catch(NumberFormatException e){
	p.sendMessage("参数格式错误，请查看帮助");
	return true;
}
task=new clipReplaceTask(m,(((oldbi&0xff)<<16)|(oldbd&0xffff)),(((newbi&0xff)<<16)|(newbd&0xffff)));
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "size":
BlockVector3 size=ClipboardStore.get(m).getSize();
p.sendMessage("剪切板大小:("+size.x+","+size.y+","+size.z+")");
break;
case "rx":
task=new clipRotationXTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "ry":
task=new clipRotationYTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "rz":
task=new clipRotationZTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "rxy":
task=new cilpRotationXYTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "ryz":
task=new cilpRotationYZTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;
case "rxz":
task=new cilpRotationXZTask(m);
this.server.getScheduler().scheduleAsyncTask(this.plugin,task);
p.sendMessage("已添加至任务列表");
break;




case "help":
default:
sender.sendMessage("we命令列表:");
sender.sendMessage("copy <int:sx> <int:sy> <int:sz> <int:ex> <int:ey> <int:ez>");
sender.sendMessage("parse <int:sx> <int:sy> <int:sz>");
sender.sendMessage("brush <int:blockid> <int:blickdata>");
sender.sendMessage("replace <int:oldbid> <int:oldbdata> <int:newbid> <int:newbdata>");
sender.sendMessage("size");
break;
}
return true;
}

}
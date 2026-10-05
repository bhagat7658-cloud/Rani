package com.proudrani.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    ProudView view;
    final int PINK = Color.rgb(205, 28, 93);
    final int DARK = Color.rgb(54, 24, 40);

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(255,245,248));
        getWindow().setNavigationBarColor(Color.WHITE);
        view = new ProudView(this);
        setContentView(view);
    }

    void dial(String number) {
        try { startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))); }
        catch (Exception e) { Toast.makeText(this, "Unable to open dialer", Toast.LENGTH_SHORT).show(); }
    }

    void sos() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Emergency SOS")
                .setMessage("If you are in immediate danger, call 112 first. Proud Rani can open the emergency dialer for you.")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("CALL 112", (d,w) -> dial("112"))
                .show();
    }

    class ProudView extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap logo, fullLogo;
        int screen = 0;
        boolean splash = true;
        float S = 1f;
        final int BG = Color.rgb(255,248,250);
        final int LIGHT = Color.rgb(255,235,242);
        final int PURPLE = Color.rgb(112,72,218);
        final int GREEN = Color.rgb(38,175,105);

        ProudView(Context c) {
            super(c);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.proud_rani_logo);
            fullLogo = BitmapFactory.decodeResource(getResources(), R.drawable.proud_rani_full);
            postDelayed(() -> { splash = false; screen = 1; invalidate(); }, 2100);
        }

        float d(float v) { return v * S; }
        void text(Canvas c,String s,float x,float y,float size,int color,boolean bold) {
            p.setStyle(Paint.Style.FILL); p.setColor(color); p.setTextSize(d(size));
            p.setTypeface(Typeface.create("sans", bold ? Typeface.BOLD : Typeface.NORMAL));
            c.drawText(s,d(x),d(y),p);
        }
        void center(Canvas c,String s,float y,float size,int color,boolean bold) {
            p.setTextSize(d(size)); p.setTypeface(Typeface.create("sans",bold?Typeface.BOLD:Typeface.NORMAL));
            text(c,s,200-p.measureText(s)/d(2),y,size,color,bold);
        }
        void round(Canvas c,float l,float t,float r,float b,float rad,int color) {
            p.setStyle(Paint.Style.FILL); p.setColor(color); c.drawRoundRect(d(l),d(t),d(r),d(b),d(rad),d(rad),p);
        }
        void line(Canvas c,float x1,float y1,float x2,float y2,int color,float sw){p.setColor(color);p.setStrokeWidth(d(sw));p.setStyle(Paint.Style.STROKE);c.drawLine(d(x1),d(y1),d(x2),d(y2),p);p.setStyle(Paint.Style.FILL);}
        void image(Canvas c,Bitmap b,float l,float t,float r,float bot){if(b==null)return; c.drawBitmap(b,null,new RectF(d(l),d(t),d(r),d(bot)),p);}

        @Override protected void onDraw(Canvas c) {
            S = getWidth()/400f;
            c.drawColor(BG);
            if (splash) splash(c); else if(screen==1) login(c); else if(screen==2) home(c); else if(screen==3) report(c); else if(screen==4) resources(c); else if(screen==5) community(c); else if(screen==6) profile(c); else if(screen==7) safeMap(c);
        }

        void splash(Canvas c) {
            c.drawColor(Color.rgb(255,226,237));
            image(c, fullLogo, 52, 55, 348, 355);
            center(c,"Safer Spaces",405,17,DARK,false);
            center(c,"Brighter Tomorrows",430,17,DARK,false);
            round(c,105,480,295,485,4,Color.rgb(247,192,211));
            round(c,105,480,205,485,4,PINK);
        }

        void login(Canvas c) {
            image(c,logo,118,22,282,175);
            center(c,"Welcome to Proud Rani",205,23,DARK,true);
            center(c,"A safer, stronger, more equal tomorrow.",232,13,Color.GRAY,false);
            round(c,25,265,375,320,17,PINK); center(c,"G   Continue with Google",300,15,Color.WHITE,true);
            center(c,"OR",350,12,Color.GRAY,false);
            round(c,25,370,375,424,15,Color.WHITE); p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(d(1));p.setColor(Color.rgb(230,220,225));c.drawRoundRect(d(25),d(370),d(375),d(424),d(15),d(15),p);p.setStyle(Paint.Style.FILL);
            text(c,"▢",43,404,16,Color.GRAY,false); text(c,"Enter mobile number",72,404,14,Color.GRAY,false);
            round(c,25,440,375,493,17,Color.rgb(246,155,188)); center(c,"Send OTP",473,15,Color.WHITE,true);
            center(c,"By continuing, you agree to our",535,11,Color.GRAY,false); center(c,"Terms & Privacy Policy",555,11,PINK,true);
        }

        void top(Canvas c,String title){text(c,"‹",17,45,32,DARK,false); center(c,title,40,17,DARK,true);}

        void home(Canvas c) {
            round(c,0,0,400,112,0,PINK);
            text(c,"Hello Rani 👋",22,39,23,Color.WHITE,true); text(c,"You are not alone.",22,65,14,Color.WHITE,false); text(c,"♧",356,48,24,Color.WHITE,false);
            tile(c,18,130,193,218,"🚨","SOS","Emergency",Color.rgb(230,45,48),true);
            tile(c,207,130,382,218,"▤","Report","Incident",LIGHT,false);
            tile(c,18,231,193,319,"●","Find","Safe Places",PURPLE,true);
            tile(c,207,231,382,319,"⚖","Legal","Support",GREEN,true);
            text(c,"Quick Resources",20,355,19,DARK,true); text(c,"See All",330,355,12,PINK,true);
            item(c,374,"☎","Women Helpline Numbers"); item(c,421,"♥","Mental Health Support"); item(c,468,"⚖","Know Your Rights"); item(c,515,"✓","Safety Tips");
            nav(c,552,0);
        }
        void tile(Canvas c,float l,float t,float r,float b,String icon,String a,String btxt,int color,boolean white){round(c,l,t,r,b,17,color);float mid=(l+r)/2;centerAt(c,icon,mid,t+33,23,white?Color.WHITE:PINK,true);centerAt(c,a,mid,t+58,13,white?Color.WHITE:PINK,true);centerAt(c,btxt,mid,t+76,12,white?Color.WHITE:PINK,false);}
        void centerAt(Canvas c,String s,float x,float y,float size,int color,boolean bold){p.setTextSize(d(size));p.setTypeface(Typeface.create("sans",bold?Typeface.BOLD:Typeface.NORMAL));text(c,s,x-p.measureText(s)/d(2),y,size,color,bold);}
        void item(Canvas c,float y,String icon,String label){round(c,17,y,383,y+38,13,Color.WHITE);text(c,icon,30,y+26,17,PINK,true);text(c,label,62,y+25,13,DARK,false);text(c,"›",357,y+25,20,Color.GRAY,false);}

        void nav(Canvas c,float y,int active){round(c,0,y,400,610,0,Color.WHITE);String[] ic={"⌂","⌖","▣","♣","●"};String[] lab={"Home","Map","Report","Community","Profile"};for(int i=0;i<5;i++){float x=40+i*80;int col=i==active?PINK:Color.GRAY;centerAt(c,ic[i],x,y+20,18,col,true);centerAt(c,lab[i],x,y+41,9,col,false);}}

        void report(Canvas c){top(c,"Report Incident"); option(c,72,"♟","Harassment","Street, workplace, online etc.");option(c,143,"✊","Assault","Physical or sexual violence");option(c,214,"◉","Stalking","Repeated unwanted attention");option(c,285,"…","Others","Any other incident");round(c,20,515,380,570,18,PINK);center(c,"Next",550,15,Color.WHITE,true);}
        void option(Canvas c,float y,String icon,String title,String sub){round(c,18,y,382,y+58,16,Color.WHITE);text(c,icon,31,y+37,20,PINK,true);text(c,title,70,y+26,14,DARK,true);text(c,sub,70,y+46,10,Color.GRAY,false);text(c,"›",355,y+36,21,Color.GRAY,false);}

        void resources(Canvas c){top(c,"Resources & Help");option(c,72,"☎","Emergency Helplines","Important contact numbers");option(c,141,"⚖","Legal Aid","Free legal support");option(c,210,"♥","Mental Health Support","Counselling and support");option(c,279,"♣","NGOs & Support Groups","Verified organisations");option(c,348,"▣","Safety Guides","Tips and resources");option(c,417,"▥","Government Schemes","Policies for women");}

        void community(Canvas c){top(c,"Community");text(c,"⌕",354,43,27,DARK,false);String[] tabs={"All","Discussions","Support","Stories"};for(int i=0;i<4;i++){float l=17+i*92;round(c,l,62,l+82,94,14,i==0?PINK:Color.rgb(248,241,245));centerAt(c,tabs[i],l+41,83,10,i==0?Color.WHITE:DARK,true);}post(c,112,"Rani123","Sharing my story because I want other women to know they are not alone. 💜","124     18");post(c,235,"SafeTogether","Some important safety tips for travelling in the city. Let's keep each other safe!","86     10");post(c,358,"Aisha","This app is amazing. Finally a space where we can support each other. ❤️","210     25");nav(c,552,3);}
        void post(Canvas c,float y,String n,String body,String stats){round(c,15,y,385,y+110,14,Color.WHITE);text(c,n,50,y+21,13,DARK,true);text(c,"2 hours ago",50,y+39,9,Color.GRAY,false);text(c,body,20,y+65,11,DARK,false);text(c,"♡  "+stats+"             ↗",22,y+94,11,Color.GRAY,false);}

        void profile(Canvas c){top(c,"My Profile");text(c,"⚙",356,43,21,DARK,false);round(c,24,67,104,147,40,LIGHT);centerAt(c,"♛",64,115,28,PINK,true);text(c,"Rani",122,98,21,DARK,true);text(c,"+91 98765 43210",122,122,12,Color.GRAY,false);item(c,175,"▣","My Reports");item(c,222,"⌖","Saved Places");item(c,269,"▰","My Posts");item(c,316,"♟","Emergency Contacts");item(c,363,"⚙","App Settings");item(c,410,"?","Help & Feedback");round(c,35,465,365,515,15,LIGHT);center(c,"Logout",497,13,PINK,true);nav(c,552,4);}

        void safeMap(Canvas c){c.drawColor(Color.rgb(226,239,226));round(c,18,18,382,70,17,Color.WHITE);text(c,"⌕  Search safe places...",32,51,13,Color.GRAY,false);for(int i=0;i<7;i++){float x=45+(i*61)%315,y=120+(i*77)%350;p.setColor(PINK);c.drawCircle(d(x),d(y),d(8),p);p.setColor(Color.WHITE);c.drawCircle(d(x),d(y),d(3),p);}round(c,18,420,382,590,20,Color.WHITE);text(c,"Women Help Centre",35,458,17,DARK,true);text(c,"2.1 km",35,482,11,Color.GRAY,false);text(c,"● Open 24x7",35,502,11,GREEN,true);round(c,35,520,365,568,15,PINK);center(c,"↗  Get Directions",550,13,Color.WHITE,true);}

        @Override public boolean onTouchEvent(MotionEvent e){if(e.getAction()!=MotionEvent.ACTION_UP)return true;float x=e.getX()/S,y=e.getY()/S;
            if(splash)return true;
            if(screen==1){if(y>255&&y<510){screen=2;invalidate();}return true;}
            if(screen==2){if(y>125&&y<225&&x<200){sos();return true;}if(y>125&&y<225){screen=3;}else if(y>225&&y<330&&x<200){screen=7;}else if(y>225&&y<330){screen=4;}else if(y>540){navigate(x);}invalidate();return true;}
            if(screen==3||screen==4){if(y<65){screen=2;invalidate();}return true;}
            if(screen==5||screen==6||screen==7){if(y>540){navigate(x);invalidate();}else if(screen==6&&y>300&&y<370){dial("9055501242");}else if(screen==7&&y>500){Toast.makeText(MainActivity.this,"Directions can be added with Maps later.",Toast.LENGTH_SHORT).show();}return true;}return true;}
        void navigate(float x){int n=(int)(x/80);screen=n==0?2:n==1?7:n==2?3:n==3?5:6;}
    }
}

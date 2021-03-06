package com.example.hoctienganh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hoctienganh.activity.ActivityLogin;
import com.example.hoctienganh.activity.ActivityTest;
import com.example.hoctienganh.adapter.ViewPagerAdapter;
import com.example.hoctienganh.database.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private Button btnNewTest;
    private Data data = new Data();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        MyData.sharedPreferences = sharedPreferences;

        initUi();
        loadSound();
        MyData.soundBackground.start();

        readData();
        MyData.data = data;
        data = null;

        btnNewTest = this.findViewById(R.id.btn_new_test);
        btnNewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData.soundEffect.start();
                actionNewTest();
            }
        });

        viewPager2 = this.findViewById(R.id.viewpager_main);
        viewPager2.setUserInputEnabled(false);
        bottomNavigationView = this.findViewById(R.id.bottom_navigation_main);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home){
                    viewPager2.setCurrentItem(0);
                }
                else if (id == R.id.bottom_user){
                    viewPager2.setCurrentItem(1);
                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_user).setChecked(true);
                        break;
                }
            }
        });
    }

    private void actionNewTest(){
        if (MyData.user == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Th??ng b??o!");
            builder.setMessage("????ng nh???p ????? ti???p t???c!");
            builder.setCancelable(false);

            builder.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentLogin = new Intent(MainActivity.this, ActivityLogin.class);
                    startActivity(intentLogin);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    return;
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
        else {
            Intent intentNewTest = new Intent(this, ActivityTest.class);
            startActivity(intentNewTest);
        }
    }

    private void initUi(){
        String name = MyData.sharedPreferences.getString(MyData.USERNAME, "");
        String pass = MyData.sharedPreferences.getString(MyData.PASSWORD, "");
        if (name.equals("") || pass.equals("")){
            return;
        }
        List<User> list = UserDatabase.getInstance(this).userDAO().getListUser(name);
        if (list.size() != 0){
            User user = list.get(0);
            if (user.getPassword().equals(pass)){
                MyData.user = user;
            }
        }
    }

    private void loadSound(){
        Boolean isSound = MyData.sharedPreferences.getBoolean(MyData.HAVE_SOUND, true);
        Boolean isEffect = MyData.sharedPreferences.getBoolean(MyData.HAVE_EFFECT, true);

        if (isSound) {
            MediaPlayer soundTrue = MediaPlayer.create(this, R.raw.nhacnen);
            MediaPlayer soundFalse = MediaPlayer.create(this, R.raw.nosound);
            soundTrue.setLooping(true);
            MyData.soundBackground = soundTrue;
            MyData.nhacnenCo = soundTrue;
            MyData.nhacnenKhong = soundFalse;
        }
        else {
            MediaPlayer soundFalse = MediaPlayer.create(this, R.raw.nosound);
            soundFalse.setVolume(0,0);
            MediaPlayer soundTrue = MediaPlayer.create(this, R.raw.nhacnen);
            soundFalse.setLooping(true);
            MyData.soundBackground = soundFalse;
            MyData.nhacnenKhong = soundFalse;
            MyData.nhacnenCo = soundTrue;
        }

        MyData.soundBackground.start();

        if (isEffect) {
            MediaPlayer soundFalse2 = MediaPlayer.create(this, R.raw.nosound);
            MediaPlayer soundTrue2 = MediaPlayer.create(this, R.raw.clickbutton);
            MyData.soundEffect = soundTrue2;
            MyData.hieuungCo = soundTrue2;
            MyData.hieuungKhong = soundFalse2;
        }

        else {
            MediaPlayer soundTrue2 = MediaPlayer.create(this, R.raw.clickbutton);
            MediaPlayer soundFalse2 = MediaPlayer.create(this, R.raw.nosound);
            soundFalse2.setVolume(0,0);
            MyData.soundEffect = soundFalse2;
            MyData.hieuungCo = soundTrue2;
            MyData.hieuungKhong = soundFalse2;
        }

    }

    private void readData(){
        //animal
        data.getListAnimal().add(new Word("dog","con ch??", R.drawable.dog));
        data.getListAnimal().add(new Word("cat","con m??o", R.drawable.cat));
        data.getListAnimal().add(new Word("pig","con l???n", R.drawable.pig));
        data.getListAnimal().add(new Word("duck","con v???t", R.drawable.duck));
        data.getListAnimal().add(new Word("goose","con ng???ng", R.drawable.goose));
        data.getListAnimal().add(new Word("chicken","con g??", R.drawable.chicken));
        data.getListAnimal().add(new Word("rabbit","con th???", R.drawable.rabbit));
        data.getListAnimal().add(new Word("buffalo","con tr??u", R.drawable.buffalo));
        data.getListAnimal().add(new Word("horse","con ng???a", R.drawable.horse));
        data.getListAnimal().add(new Word("donkey","con l???a", R.drawable.donkey));
        data.getListAnimal().add(new Word("sheep","con c???u", R.drawable.sheep));
        data.getListAnimal().add(new Word("goat","con d??", R.drawable.goat));
        data.getListAnimal().add(new Word("cow","con b??", R.drawable.cow));
        data.getListAnimal().add(new Word("mouse","con chu???t", R.drawable.mouse));
        data.getListAnimal().add(new Word("monkey","con kh???", R.drawable.monkey));
        data.getListAnimal().add(new Word("lion","con s?? t???", R.drawable.lion));
        data.getListAnimal().add(new Word("tiger","con h???", R.drawable.tiger));
        data.getListAnimal().add(new Word("elephant","con voi", R.drawable.elephant));
        data.getListAnimal().add(new Word("fox","con c??o", R.drawable.fox));
        data.getListAnimal().add(new Word("bear","con g???u", R.drawable.bear));
        data.getListAnimal().add(new Word("giraffe","h????u cao c???", R.drawable.giraffe));
        data.getListAnimal().add(new Word("hippopotamus","con h?? m??", R.drawable.hippopotamus));
        data.getListAnimal().add(new Word("jaguar","con b??o", R.drawable.jaguar));
        data.getListAnimal().add(new Word("porcupine","con nh??m", R.drawable.porcupine));
        data.getListAnimal().add(new Word("rhinoceros","con t??? gi??c", R.drawable.rhinoceros));
        data.getListAnimal().add(new Word("squirrel","con s??c", R.drawable.squirrel));
        data.getListAnimal().add(new Word("crocodile","con c?? s???u", R.drawable.crocodile));
        data.getListAnimal().add(new Word("bat","con d??i", R.drawable.bat));
        data.getListAnimal().add(new Word("camel","con l???c ????", R.drawable.camel));
        data.getListAnimal().add(new Word("kangaroo","con chu???t t??i", R.drawable.kangaroo));
        data.getListAnimal().add(new Word("panda","con g???u tr??c", R.drawable.panda));
        data.getListAnimal().add(new Word("deer","con h????u", R.drawable.deer));
        data.getListAnimal().add(new Word("wolf","con s??i", R.drawable.wolf));
        data.getListAnimal().add(new Word("hedgehog","con nh??m", R.drawable.hedgehog));
        data.getListAnimal().add(new Word("zebra","con ng???a v???n", R.drawable.zebra));
        data.getListAnimal().add(new Word("penguin","chim c??nh c???t", R.drawable.penguin));
        data.getListAnimal().add(new Word("dolphin","c?? voi", R.drawable.dolphin));
        data.getListAnimal().add(new Word("shark","c?? m???p", R.drawable.shark));
        data.getListAnimal().add(new Word("whale","c?? voi", R.drawable.whale));
        data.getListAnimal().add(new Word("turtle","con r??a", R.drawable.turtle));
        data.getListAnimal().add(new Word("jellyfish","con s???a", R.drawable.jellyfish));
        data.getListAnimal().add(new Word("octopus","b???ch tu???c", R.drawable.octopus));
        data.getListAnimal().add(new Word("squid","con m???c", R.drawable.squid));
        data.getListAnimal().add(new Word("starfish","sao bi???n", R.drawable.starfish));
        data.getListAnimal().add(new Word("clownfish","c?? h???", R.drawable.clownfish));
        data.getListAnimal().add(new Word("crab","con cua", R.drawable.crab));
        data.getListAnimal().add(new Word("seahorse","c?? ng???a", R.drawable.seahorse));
        data.getListAnimal().add(new Word("seal","h???i c???u", R.drawable.seal));
        data.getListAnimal().add(new Word("owl","con c??", R.drawable.owl));
        data.getListAnimal().add(new Word("turkey","g?? t??y", R.drawable.turkey));
        data.getListAnimal().add(new Word("parrot","con v???t", R.drawable.parrot));
        data.getListAnimal().add(new Word("ostrich","???? ??i???u", R.drawable.ostrich));
        data.getListAnimal().add(new Word("pigeon","b??? c??u", R.drawable.pigeon));
        data.getListAnimal().add(new Word("eagle","?????i b??ng", R.drawable.eagle));
        data.getListAnimal().add(new Word("crow","con qu???", R.drawable.crow));
        data.getListAnimal().add(new Word("sparrow","chim s???", R.drawable.sparrow));
        data.getListAnimal().add(new Word("fly","con ru???i", R.drawable.fly));
        data.getListAnimal().add(new Word("mosquito","con mu???i", R.drawable.mosquito));
        data.getListAnimal().add(new Word("butterfly","con b?????m", R.drawable.butterfly));
        data.getListAnimal().add(new Word("dragonfly","chu???n chu???n", R.drawable.dragonfly));
        data.getListAnimal().add(new Word("cockroach","con gi??n", R.drawable.cockroach));
        data.getListAnimal().add(new Word("ant","con ki???n", R.drawable.ant));

        //color

        data.getListColor().add(new Word("black","m??u ??en",R.drawable.blackcolor));
        data.getListColor().add(new Word("blue","m??u xanh d????ng",R.drawable.bluecolor));
        data.getListColor().add(new Word("gray","m??u x??m",R.drawable.graycolor));
        data.getListColor().add(new Word("brown","m??u n??u",R.drawable.browncolor));
        data.getListColor().add(new Word("green","m??u xanh l??",R.drawable.greencolor));
        data.getListColor().add(new Word("orange","m??u cam",R.drawable.orangecolor));
        data.getListColor().add(new Word("pink","m??u h???ng",R.drawable.pinkcolor));
        data.getListColor().add(new Word("purple","m??u t??m",R.drawable.purplecolor));
        data.getListColor().add(new Word("red","m??u ?????",R.drawable.redcolor));
        data.getListColor().add(new Word("white","m??u tr???ng",R.drawable.whitecolor));
        data.getListColor().add(new Word("yellow","m??u v??ng",R.drawable.yellocolor));


        //clothe
        data.getListClothes().add(new Word("dress", "chi???c v??y", R.drawable.dress));
        data.getListClothes().add(new Word("pants", "qu???n t??y", R.drawable.pants));
        data.getListClothes().add(new Word("short", "qu???n ????i", R.drawable.shorts));
        data.getListClothes().add(new Word("shirt", "??o s?? mi", R.drawable.shirt));
        data.getListClothes().add(new Word("T-shirt", "??o thun", R.drawable.tshirt));
        data.getListClothes().add(new Word("suit", "b??? ????? vest", R.drawable.suit));
        data.getListClothes().add(new Word("jacket", "??o kho??c", R.drawable.jacket));
        data.getListClothes().add(new Word("gloves", "g??ng tay", R.drawable.gloves));
        data.getListClothes().add(new Word("belt", "th???t l??ng", R.drawable.belt));
        data.getListClothes().add(new Word("cap", "m?? l?????i trai", R.drawable.cap));
        data.getListClothes().add(new Word("jeans", "qu???n jeans", R.drawable.jeans));
        data.getListClothes().add(new Word("scarf", "kh??n qu??ng c???", R.drawable.scarf));
        data.getListClothes().add(new Word("shoes", "????i gi??y", R.drawable.shoes));
        data.getListClothes().add(new Word("wallet", "chi???c v??", R.drawable.wallet));
        data.getListClothes().add(new Word("socks", "????i t???t", R.drawable.socks));
        data.getListClothes().add(new Word("hat", "c??i m??", R.drawable.hat));
        data.getListClothes().add(new Word("glasses", "k??nh", R.drawable.glasses));
        data.getListClothes().add(new Word("handbag", "t??i x??ch", R.drawable.handbag));
        data.getListClothes().add(new Word("skirt", "ch??n v??y", R.drawable.skirt));
        data.getListClothes().add(new Word("sweater", "??o len", R.drawable.sweater));
        data.getListClothes().add(new Word("tie", "c?? v???t", R.drawable.tie));

        //fruit
        data.getListFruit().add(new Word("apple", "qu??? t??o", R.drawable.apple));
        data.getListFruit().add(new Word("grape", "qu??? nho", R.drawable.grape));
        data.getListFruit().add(new Word("banana", "qu??? chu???i", R.drawable.banana));
        data.getListFruit().add(new Word("pear", "qu??? l??", R.drawable.pear));
        data.getListFruit().add(new Word("orange", "qu??? cam", R.drawable.orange));
        data.getListFruit().add(new Word("pineapple", "qu??? d???a", R.drawable.pineapple));
        data.getListFruit().add(new Word("peach", "qu??? ????o", R.drawable.peach));
        data.getListFruit().add(new Word("dragon fruit", "qu??? thanh long", R.drawable.dragon_fruit));
        data.getListFruit().add(new Word("starfruit", "qu??? kh???", R.drawable.starfruit));
        data.getListFruit().add(new Word("jackfruit", "qu??? m??t", R.drawable.jackfruit));
        data.getListFruit().add(new Word("guava", "qu??? ???i", R.drawable.guava));
        data.getListFruit().add(new Word("mango", "qu??? xo??i", R.drawable.mango));
        data.getListFruit().add(new Word("coconut", "qu??? d???a", R.drawable.coconut));
        data.getListFruit().add(new Word("durian", "qu??? s???u ri??ng", R.drawable.durian));
        data.getListFruit().add(new Word("avocado", "qu??? b??", R.drawable.avocado));
        data.getListFruit().add(new Word("pomelo", "qu??? b?????i", R.drawable.pomelo));
        data.getListFruit().add(new Word("apricot", "qu??? m??", R.drawable.apricot));
        data.getListFruit().add(new Word("papaya", "qu??? ??u ?????", R.drawable.papaya));
        data.getListFruit().add(new Word("mangosteen", "qu??? m??ng c???t", R.drawable.mangosteen));
        data.getListFruit().add(new Word("rambutan", "qu??? ch??m ch??m", R.drawable.rambutan));
        data.getListFruit().add(new Word("blackberry", "qu??? m??m x??i", R.drawable.blackberry));
        data.getListFruit().add(new Word("stawberry", "d??u t??y", R.drawable.stawberry));
        data.getListFruit().add(new Word("longan", "qu??? nh??n", R.drawable.longan));
        data.getListFruit().add(new Word("lychee", "qu??? v???i", R.drawable.lychee));
        data.getListFruit().add(new Word("persimmon", "qu??? h???ng", R.drawable.persimmon));
        data.getListFruit().add(new Word("kiwi", "qu??? kiwi", R.drawable.kiwi));
        data.getListFruit().add(new Word("plum", "qu??? m???n", R.drawable.plum));
        data.getListFruit().add(new Word("watermelon", "qu??? d??a h???u", R.drawable.watermelon));
        data.getListFruit().add(new Word("lemon", "qu??? chanh", R.drawable.lemon));
        data.getListFruit().add(new Word("cherry", "qu??? anh ????o", R.drawable.cherry));


        //body
        data.getListBody().add(new Word("eye", "m???t", R.drawable.eye));
        data.getListBody().add(new Word("nose", "m??i", R.drawable.nose));
        data.getListBody().add(new Word("mouth", "mi???ng", R.drawable.mouth));
        data.getListBody().add(new Word("hair", "t??c", R.drawable.hair));
        data.getListBody().add(new Word("ear", "tai", R.drawable.ear));
        data.getListBody().add(new Word("hand", "b??n tay", R.drawable.hand));
        data.getListBody().add(new Word("arm", "c??nh tay", R.drawable.arm));
        data.getListBody().add(new Word("neck", "c???", R.drawable.neck));
        data.getListBody().add(new Word("stomach", "b???ng", R.drawable.stomach));
        data.getListBody().add(new Word("leg", "ch??n", R.drawable.leg));
        data.getListBody().add(new Word("foot", "b??n ch??n", R.drawable.foot));
        data.getListBody().add(new Word("forehead", "tr??n", R.drawable.forehead));
        data.getListBody().add(new Word("eyebrow", "l??ng m??y", R.drawable.eyebrow));
        data.getListBody().add(new Word("tooth", "r??ng", R.drawable.tooth));
        data.getListBody().add(new Word("cheek", "m??", R.drawable.cheek));
        data.getListBody().add(new Word("chin", "c???m", R.drawable.chin));
        data.getListBody().add(new Word("finger", "ng??n tay", R.drawable.finger));
        data.getListBody().add(new Word("elbow", "khu???u tay", R.drawable.elbow));
        data.getListBody().add(new Word("knee", "?????u g???i", R.drawable.knee));
        data.getListBody().add(new Word("shoulder", "vai", R.drawable.shoulder));

        //country
        data.getListCountry().add(new Word("Denmark", "??an M???ch", R.drawable.danmark));
        data.getListCountry().add(new Word("England", "Anh Qu???c", R.drawable.england));
        data.getListCountry().add(new Word("Finland", "Ph???n Lan", R.drawable.finland));
        data.getListCountry().add(new Word("Sweden", "Th???y ??i???n", R.drawable.sweden));
        data.getListCountry().add(new Word("Austria", "??o", R.drawable.austria));
        data.getListCountry().add(new Word("France", "Ph??p", R.drawable.france));
        data.getListCountry().add(new Word("Belgium", "B???", R.drawable.belgium));
        data.getListCountry().add(new Word("Germany", "?????c", R.drawable.germany));
        data.getListCountry().add(new Word("Netherlands", "H?? Lan", R.drawable.netherlands));
        data.getListCountry().add(new Word("Switzerland", "Th???y S???", R.drawable.switzerland));
        data.getListCountry().add(new Word("Italy", "??", R.drawable.italy));
        data.getListCountry().add(new Word("Greece", "Hi L???p", R.drawable.greece));
        data.getListCountry().add(new Word("Portugal", "B??? ????o Nha", R.drawable.portugal));
        data.getListCountry().add(new Word("Spain", "T??y Ban Nha", R.drawable.spain));
        data.getListCountry().add(new Word("Russia", "Nga", R.drawable.russia));
        data.getListCountry().add(new Word("Canada", "Ca-na-da", R.drawable.canada));
        data.getListCountry().add(new Word("United States", "Hoa K???", R.drawable.united_states));
        data.getListCountry().add(new Word("Brazil", "Bra-xin", R.drawable.brazil));
        data.getListCountry().add(new Word("Argentina", "??c-hen-ti-na", R.drawable.argentina));
        data.getListCountry().add(new Word("India", "???n ?????", R.drawable.india));
        data.getListCountry().add(new Word("China", "Trung Qu???c", R.drawable.china));
        data.getListCountry().add(new Word("Japan", "Nh???t B???n", R.drawable.japan));
        data.getListCountry().add(new Word("North Korea", "Tri???u Ti??n", R.drawable.north_korea));
        data.getListCountry().add(new Word("South Korea", "H??n Qu???c", R.drawable.south_korea));
        data.getListCountry().add(new Word("Cambodia", "Cam-pu-chia", R.drawable.cambodia));
        data.getListCountry().add(new Word("Indonesia", "In-????-n??-si-a", R.drawable.indonesia));
        data.getListCountry().add(new Word("Laos", "L??o", R.drawable.laos));
        data.getListCountry().add(new Word("Malaysia", "Ma-lai-si-a", R.drawable.malaysia));
        data.getListCountry().add(new Word("Myanmar", "Mi-an-ma", R.drawable.myanmar));
        data.getListCountry().add(new Word("Philippines", "Phi-l??p-pin", R.drawable.philippines));
        data.getListCountry().add(new Word("Singapore", "Sinh-ga-po", R.drawable.singapore));
        data.getListCountry().add(new Word("Thailand", "Th??i Lan", R.drawable.thailand));
        data.getListCountry().add(new Word("Vietnam", "Vi???t Nam", R.drawable.vietnam));
        data.getListCountry().add(new Word("Australia", "??c", R.drawable.australia));
        data.getListCountry().add(new Word("Egypt", "Ai C???p", R.drawable.egypt));
        data.getListCountry().add(new Word("South Africa", "Nam Phi", R.drawable.south_africa));

        //drink
        data.getListDrink().add(new Word("coffee", "c?? ph??", R.drawable.coffee));
        data.getListDrink().add(new Word("beer", "bia", R.drawable.beer));
        data.getListDrink().add(new Word("wine", "r?????u", R.drawable.wine));
        data.getListDrink().add(new Word("juice", "n?????c ??p tr??i c??y", R.drawable.juice));
        data.getListDrink().add(new Word("tea", "tr??", R.drawable.tea));
        data.getListDrink().add(new Word("soft drink", "n?????c ng???t", R.drawable.soft_drink));
        data.getListDrink().add(new Word("mineral water", "n?????c kho??ng", R.drawable.mineral_water));
        data.getListDrink().add(new Word("cocktail", "c???c-tai", R.drawable.cocktail));
        data.getListDrink().add(new Word("lemonade", "n?????c chanh", R.drawable.lemonade));

        //flower
        data.getListFlower().add((new Word("daisy", "hoa c??c", R.drawable.daisy)));
        data.getListFlower().add((new Word("rose", "hoa h???ng", R.drawable.rose)));
        data.getListFlower().add((new Word("orchid", "hoa lan", R.drawable.orchid)));
        data.getListFlower().add((new Word("tulip", "hoa tu-lip", R.drawable.tulip)));
        data.getListFlower().add((new Word("sunflower", "hoa h?????ng d????ng", R.drawable.sunflower)));
        data.getListFlower().add((new Word("lily", "hoa loa k??n", R.drawable.lily)));
        data.getListFlower().add((new Word("carnation", "hoa c???m ch?????ng", R.drawable.carnation)));
        data.getListFlower().add((new Word("mimosa", "hoa x???u h???", R.drawable.mimosa)));
        data.getListFlower().add((new Word("gladiolus", "hoa lay-??n", R.drawable.gladiolus)));
        data.getListFlower().add((new Word("lotus", "hoa sen", R.drawable.lotus)));
        data.getListFlower().add((new Word("sakura", "hoa anh ????o", R.drawable.sakura)));
        data.getListFlower().add((new Word("water lily", "hoa s??ng", R.drawable.water_lily)));
        data.getListFlower().add((new Word("peony", "hoa m???u ????n", R.drawable.peony)));
        data.getListFlower().add((new Word("dandelion", "b??? c??ng anh", R.drawable.dandelion)));
        data.getListFlower().add((new Word("tuberose", "hoa hu???", R.drawable.tuberose)));
        data.getListFlower().add((new Word("hibiscus", "hoa d??m b???t", R.drawable.hibiscus)));
        data.getListFlower().add((new Word("gerbera", "hoa ?????ng ti???n", R.drawable.gerbera)));
        data.getListFlower().add((new Word("flamboyant", "hoa ph?????ng", R.drawable.flamboyant)));

        //food

        data.getListFood().add(new Word("bread", "b??nh m??", R.drawable.bread));
        data.getListFood().add(new Word("egg", "tr???ng", R.drawable.egg));
        data.getListFood().add(new Word("cheese", "ph?? mai", R.drawable.cheese));
        data.getListFood().add(new Word("rice", "c??m", R.drawable.rice));
        data.getListFood().add(new Word("pizza", "b??nh pizza", R.drawable.pizza));
        data.getListFood().add(new Word("yogurt", "s???a chua", R.drawable.yogurt));
        data.getListFood().add(new Word("cake", "b??nh ng???t", R.drawable.cake));
        data.getListFood().add(new Word("chicken", "th???t g??", R.drawable.chickenfood));
        data.getListFood().add(new Word("pork", "th???t l???n", R.drawable.pork));
        data.getListFood().add(new Word("beef", "th???t b??", R.drawable.beef));
        data.getListFood().add(new Word("fish", "c??", R.drawable.fish));
        data.getListFood().add(new Word("shrimp", "t??m", R.drawable.shrimp));
        data.getListFood().add(new Word("lobster", "t??m h??m", R.drawable.lobster));
        data.getListFood().add(new Word("clam", "ngh??u", R.drawable.clam));
        data.getListFood().add(new Word("mussel", "con trai", R.drawable.mussel));
        data.getListFood().add(new Word("sausage", "x??c x??ch", R.drawable.sausage));
        data.getListFood().add(new Word("steak", "b??t t???t", R.drawable.steak));
        data.getListFood().add(new Word("salt", "mu???i", R.drawable.salt));
        data.getListFood().add(new Word("sugar", "???????ng", R.drawable.sugar));
        data.getListFood().add(new Word("fish sauce", "n?????c m???m", R.drawable.fish_sauce));
        data.getListFood().add(new Word("soy sauce", "n?????c t????ng", R.drawable.soy_sauce));
        data.getListFood().add(new Word("vinegar", "d???m", R.drawable.vinegar));
        //data.getListFood().add(new Word("noodle", "m??? g??i", R.drawable.));

        //house
        data.getListHouse().add(new Word("bathroom", "nh?? t???m", R.drawable.bathroom));
        data.getListHouse().add(new Word("bedroom", "ph??ng ng???", R.drawable.bedroom));
        data.getListHouse().add(new Word("kitchen", "b???p", R.drawable.kitchen));
        data.getListHouse().add(new Word("living room", "ph??ng kh??ch", R.drawable.living_room));
        data.getListHouse().add(new Word("garden", "v?????n", R.drawable.garden));
        data.getListHouse().add(new Word("bed", "c??i gi?????ng", R.drawable.bed));
        data.getListHouse().add(new Word("fan", "c??i qu???t", R.drawable.fan));
        data.getListHouse().add(new Word("picture", "b???c tranh", R.drawable.picture));
        data.getListHouse().add(new Word("pillow", "c??i g???i", R.drawable.pillow));
        data.getListHouse().add(new Word("blanket", "ch??n", R.drawable.blanket));
        data.getListHouse().add(new Word("mattress", "n???m", R.drawable.mattress));
        data.getListHouse().add(new Word("bin", "th??ng r??c", R.drawable.bin));
        data.getListHouse().add(new Word("television", "ti vi", R.drawable.television));
        data.getListHouse().add(new Word("telephone", "??i???n tho???i b??n", R.drawable.telephone));
        data.getListHouse().add(new Word("air conditioner", "??i???u h??a", R.drawable.air_conditioner));
        data.getListHouse().add(new Word("toilet", "b???n c???u", R.drawable.toilet));
        data.getListHouse().add(new Word("washing machine", "m??y gi???t", R.drawable.washing_machine));
        data.getListHouse().add(new Word("sink", "b???n r???a", R.drawable.sink));
        data.getListHouse().add(new Word("shower", "v??i hoa sen", R.drawable.shower));
        data.getListHouse().add(new Word("tub", "b???n t???m", R.drawable.tub));
        data.getListHouse().add(new Word("toothpaste", "kem ????nh r??ng", R.drawable.toothpaste));
        data.getListHouse().add(new Word("toothbrush", "b??n ch???i ????nh r??ng", R.drawable.toothbrush));
        data.getListHouse().add(new Word("mirror", "c??i g????ng", R.drawable.mirror));
        data.getListHouse().add(new Word("razor", "dao c???o r??u", R.drawable.razor));
        data.getListHouse().add(new Word("shampoo", "d???u g???i", R.drawable.shampoo));
        data.getListHouse().add(new Word("table", "b??n", R.drawable.table));
        data.getListHouse().add(new Word("bench", "gh??? b??nh", R.drawable.bench));
        data.getListHouse().add(new Word("sofa", "gh??? s??-pha", R.drawable.sofa));
        data.getListHouse().add(new Word("vase", "l???", R.drawable.vase));
        data.getListHouse().add(new Word("fridge", "t??? l???nh", R.drawable.fridge));
        data.getListHouse().add(new Word("cooker", "n???i c??m ??i???n", R.drawable.cooker));
        data.getListHouse().add(new Word("dishwasher", "m??y r???a b??t", R.drawable.dishwasher));
        data.getListHouse().add(new Word("microwave", "l?? vi s??ng", R.drawable.microwave));
        data.getListHouse().add(new Word("apron", "t???p d???", R.drawable.apron));
        data.getListHouse().add(new Word("saucepan", "c??i n???i", R.drawable.saucepan));
        data.getListHouse().add(new Word("pan", "c??i ch???o", R.drawable.pan));
        data.getListHouse().add(new Word("colander", "c??i r???", R.drawable.colander));
        data.getListHouse().add(new Word("chopsticks", "????a", R.drawable.chopsticks));
        data.getListHouse().add(new Word("spoon", "c??i th??a", R.drawable.spoon));
        data.getListHouse().add(new Word("fork", "c??i n??a", R.drawable.fork));
        data.getListHouse().add(new Word("bowl", "b??t", R.drawable.bowl));
        data.getListHouse().add(new Word("calendar", "l???ch", R.drawable.calendar));
        data.getListHouse().add(new Word("comb", "c??i l?????c", R.drawable.comb));
        data.getListHouse().add(new Word("lamp", "????n b??n", R.drawable.lamp));
        data.getListHouse().add(new Word("cup", "c??i c???c", R.drawable.cup));
        data.getListHouse().add(new Word("knife", "con dao", R.drawable.knife));
        data.getListHouse().add(new Word("floor", "s??n nh??", R.drawable.floor));
        data.getListHouse().add(new Word("roof", "m??i nh??", R.drawable.roof));
        data.getListHouse().add(new Word("chimney", "???ng kh??i", R.drawable.chimney));

        //job
        data.getListJob().add(new Word("doctor", "b??c s???", R.drawable.doctor));
        data.getListJob().add(new Word("dentist", "nha s???", R.drawable.dentist));
        data.getListJob().add(new Word("nurse", "y t??", R.drawable.nurse));
        data.getListJob().add(new Word("teacher", "gi??o vi??n", R.drawable.teacher));
        data.getListJob().add(new Word("reporter", "ph??ng vi??n", R.drawable.reporter));
        data.getListJob().add(new Word("chef", "?????u b???p", R.drawable.chef));
        data.getListJob().add(new Word("magician", "???o thu???t gia", R.drawable.magician));
        data.getListJob().add(new Word("baker", "th??? l??m b??nh", R.drawable.baker));
        data.getListJob().add(new Word("singer", "ca s???", R.drawable.singer));
        data.getListJob().add(new Word("artist", "h???a s???", R.drawable.artist));
        data.getListJob().add(new Word("lawyer", "lu???t s??", R.drawable.lawyer));
        data.getListJob().add(new Word("policeman", "c???nh s??t", R.drawable.policeman));
        data.getListJob().add(new Word("hairdresser", "th??? c???t t??c", R.drawable.hairdresser));
        data.getListJob().add(new Word("dancer", "v?? c??ng", R.drawable.dancer));
        data.getListJob().add(new Word("farmer", "n??ng d??n", R.drawable.farmer));
        data.getListJob().add(new Word("pilot", "phi c??ng", R.drawable.pilot));
        data.getListJob().add(new Word("photographer", "nhi???p ???nh gia", R.drawable.photographer));
        data.getListJob().add(new Word("sailor", "th???y th???", R.drawable.sailor));
        data.getListJob().add(new Word("firefighter", "l??nh c???u h???a", R.drawable.firefighter));
        data.getListJob().add(new Word("astronaut", "phi h??nh gia", R.drawable.astronaut));
        data.getListJob().add(new Word("architect", "ki???n tr??c s??", R.drawable.architect));

        //sport
        data.getListSport().add(new Word("cycling", "?????p xe", R.drawable.cycling));
        data.getListSport().add(new Word("tennis", "qu???n v???t", R.drawable.tennis));
        data.getListSport().add(new Word("running", "ch???y b???", R.drawable.running));
        data.getListSport().add(new Word("swimming", "b??i l???i", R.drawable.swimming));
        data.getListSport().add(new Word("riding", "c?????i ng???a", R.drawable.riding));
        data.getListSport().add(new Word("volleyball", "b??ng chuy???n", R.drawable.volleyball));
        data.getListSport().add(new Word("football", "b??ng ????", R.drawable.football));
        data.getListSport().add(new Word("basketball", "b??ng r???", R.drawable.basketball));
        data.getListSport().add(new Word("table tennis", "b??ng b??n", R.drawable.table_tennis));
        data.getListSport().add(new Word("baseball", "b??ng ch??y", R.drawable.baseball));
        data.getListSport().add(new Word("golf", "????nh golf", R.drawable.golf));
        data.getListSport().add(new Word("badminton", "c???u l??ng", R.drawable.badminton));
        data.getListSport().add(new Word("windsurfing", "l?????t v??n bu???m", R.drawable.windsufting));
        data.getListSport().add(new Word("skiing", "tr?????t tuy???t", R.drawable.skiing));
        data.getListSport().add(new Word("skateboarding", "tr?????t v??n", R.drawable.skateboarding));

        //vehicle
        data.getListVehicle().add(new Word("car", "xe h??i", R.drawable.car));
        data.getListVehicle().add(new Word("truck", "xe t???i", R.drawable.truck));
        data.getListVehicle().add(new Word("bus", "xe bu??t", R.drawable.bus));
        data.getListVehicle().add(new Word("bicycle", "xe ?????p", R.drawable.bicycle));
        data.getListVehicle().add(new Word("motorbike", "xe m??y", R.drawable.motorbike));
        data.getListVehicle().add(new Word("train", "t??u h???a", R.drawable.train));
        //data.getListVehicle().add(new Word("subway", "t??u ??i???n ng???m", R.drawable.s));
        data.getListVehicle().add(new Word("helicopter", "tr???c th??ng", R.drawable.helicopter));
        data.getListVehicle().add(new Word("plane", "m??y bay", R.drawable.plane));
        data.getListVehicle().add(new Word("balloon", "khinh kh?? c???u", R.drawable.balloon));
        data.getListVehicle().add(new Word("ship", "t??u bi???n", R.drawable.ship));
        data.getListVehicle().add(new Word("submarine", "t??u ng???m", R.drawable.submarine));
        data.getListVehicle().add(new Word("sailboat", "thuy???n bu???m", R.drawable.sailboat));
        data.getListVehicle().add(new Word("tractor", "m??y k??o", R.drawable.tractor));
        data.getListVehicle().add(new Word("bulldozer", "m??y ???i", R.drawable.bulldozer));
        data.getListVehicle().add(new Word("excavator", "m??y x??c", R.drawable.excavator));

        //vegetable
        data.getListVegetable().add(new Word("tomato", "c?? chua", R.drawable.tomato));
        data.getListVegetable().add(new Word("carrot", "c?? r???t", R.drawable.carrot));
        data.getListVegetable().add(new Word("onion", "h??nh t??y", R.drawable.onion));
        data.getListVegetable().add(new Word("cucumber", "d??a chu???t", R.drawable.cucumber));
        data.getListVegetable().add(new Word("cauliflower", "s??p l??", R.drawable.cauliflower));
        data.getListVegetable().add(new Word("eggplant", "c?? t??m", R.drawable.eggplant));
        data.getListVegetable().add(new Word("pepper", "h???t ti??u", R.drawable.pepper));
        data.getListVegetable().add(new Word("chilli", "tr??i ???t", R.drawable.chilli));
        data.getListVegetable().add(new Word("pumpkin", "pumpkin", R.drawable.pumpkin));
        data.getListVegetable().add(new Word("sweet potato", "khoai lang", R.drawable.sweet_potato));
        data.getListVegetable().add(new Word("potato", "khoai t??y", R.drawable.potato));
        data.getListVegetable().add(new Word("cabbage", "b???p c???i", R.drawable.cabbage));
        data.getListVegetable().add(new Word("garlic", "t???i", R.drawable.garlic));
        data.getListVegetable().add(new Word("beetroot", "c??? d???n", R.drawable.beetroot));
        data.getListVegetable().add(new Word("peas", "?????u H?? Lan", R.drawable.peas));
        data.getListVegetable().add(new Word("corn", "b???p ng??", R.drawable.corn));
        data.getListVegetable().add(new Word("spinach", "rau ch??n v???t", R.drawable.spinach));
        data.getListVegetable().add(new Word("ginger", "c??? g???ng", R.drawable.ginger));
        data.getListVegetable().add(new Word("shallot", "h??nh t??m", R.drawable.shallot));
        data.getListVegetable().add(new Word("peanut", "h???t l???c", R.drawable.peanut));
        data.getListVegetable().add(new Word("lemongrass", "s???", R.drawable.lemongrass));
        data.getListVegetable().add(new Word("broccoli", "b??ng c???i xanh", R.drawable.broccoli));
        data.getListVegetable().add(new Word("asparagus", "m??ng t??y", R.drawable.asparagus));
        data.getListVegetable().add(new Word("turmeric", "c??? ngh???", R.drawable.turmeric));
        data.getListVegetable().add(new Word("kohlrabi", "su h??o", R.drawable.kohlrabi));
        data.getListVegetable().add(new Word("seaweed", "rong bi???n", R.drawable.seaweed));
        data.getListVegetable().add(new Word("mushroom", "n???m", R.drawable.mushroom));

        //alphabet
        data.getListAlphabet().add(new Word("a", "A", R.drawable.a));
        data.getListAlphabet().add(new Word("b", "B", R.drawable.b));
        data.getListAlphabet().add(new Word("c", "C", R.drawable.c));
        data.getListAlphabet().add(new Word("d", "D", R.drawable.d));
        data.getListAlphabet().add(new Word("e", "E", R.drawable.e));
        data.getListAlphabet().add(new Word("f", "F", R.drawable.f));
        data.getListAlphabet().add(new Word("g", "G", R.drawable.g));
        data.getListAlphabet().add(new Word("h", "H", R.drawable.h));
        data.getListAlphabet().add(new Word("i", "I", R.drawable.i));
        data.getListAlphabet().add(new Word("j", "J", R.drawable.j));
        data.getListAlphabet().add(new Word("k", "K", R.drawable.k));
        data.getListAlphabet().add(new Word("l", "L", R.drawable.i));
        data.getListAlphabet().add(new Word("m", "M", R.drawable.m));
        data.getListAlphabet().add(new Word("n", "N", R.drawable.n));
        data.getListAlphabet().add(new Word("o", "O", R.drawable.o));
        data.getListAlphabet().add(new Word("p", "P", R.drawable.p));
        data.getListAlphabet().add(new Word("q", "Q", R.drawable.q));
        data.getListAlphabet().add(new Word("r", "R", R.drawable.r));
        data.getListAlphabet().add(new Word("s", "S", R.drawable.s));
        data.getListAlphabet().add(new Word("t", "T", R.drawable.t));
        data.getListAlphabet().add(new Word("u", "U", R.drawable.u));
        data.getListAlphabet().add(new Word("v", "V", R.drawable.v));
        data.getListAlphabet().add(new Word("w", "W", R.drawable.w));
        data.getListAlphabet().add(new Word("x", "X", R.drawable.x));
        data.getListAlphabet().add(new Word("z", "Z", R.drawable.z));

        //number
        data.getListNumber().add(new Word("zero", "s??? kh??ng", R.drawable.number00));
        data.getListNumber().add(new Word("one", "s??? m???t", R.drawable.number01));
        data.getListNumber().add(new Word("two", "s??? hai", R.drawable.number02));
        data.getListNumber().add(new Word("three", "s??? ba", R.drawable.number03));
        data.getListNumber().add(new Word("four", "s??? b???n", R.drawable.number04));
        data.getListNumber().add(new Word("five", "s??? n??m", R.drawable.number05));
        data.getListNumber().add(new Word("six", "s??? s??u", R.drawable.number06));
        data.getListNumber().add(new Word("seven", "s??? b???y", R.drawable.number07));
        data.getListNumber().add(new Word("eight", "s??? t??m", R.drawable.number08));
        data.getListNumber().add(new Word("nine", "s??? ch??n", R.drawable.number09));
        data.getListNumber().add(new Word("ten", "s??? m?????i", R.drawable.number10));
        data.getListNumber().add(new Word("eleven", "s??? m?????i m???t", R.drawable.number11));
        data.getListNumber().add(new Word("twelve", "s??? m?????i hai", R.drawable.number12));
        data.getListNumber().add(new Word("thirteen", "s??? m?????i ba", R.drawable.number13));
        data.getListNumber().add(new Word("fourteen", "s??? m?????i b???n", R.drawable.number14));
        data.getListNumber().add(new Word("fifteen", "s??? m?????i l??m", R.drawable.number15));
        data.getListNumber().add(new Word("sixteen", "s??? m?????i s??u", R.drawable.number16));
        data.getListNumber().add(new Word("seventeen", "s??? m?????i b???y", R.drawable.number17));
        data.getListNumber().add(new Word("eighteen", "s??? m?????i t??m", R.drawable.number18));
        data.getListNumber().add(new Word("nineteen", "s??? m?????i ch??n", R.drawable.number19));
        data.getListNumber().add(new Word("twenty", "s??? hai m????i", R.drawable.number20));

        //study
        data.getListStudy().add(new Word("ruler", "th?????c k???", R.drawable.ruler));
        data.getListStudy().add(new Word("pen", "b??t m???c", R.drawable.pen));
        data.getListStudy().add(new Word("pencil", "b??t ch??", R.drawable.pencil));
        data.getListStudy().add(new Word("paper", "gi???y vi???t", R.drawable.paper));
        data.getListStudy().add(new Word("marker", "b??t l??ng", R.drawable.marker));
        data.getListStudy().add(new Word("map", "b???n ?????", R.drawable.map));
        data.getListStudy().add(new Word("highlighter", "b??t ????nh d???u", R.drawable.highlighter));
        data.getListStudy().add(new Word("glue", "keo d??n h???", R.drawable.glue));
        data.getListStudy().add(new Word("globe", "qu??? ?????a c???u", R.drawable.globe));
        data.getListStudy().add(new Word("eraser", "c???c t???y", R.drawable.eraser));
        data.getListStudy().add(new Word("dictionary", "t??? ??i???n", R.drawable.dictionary));
        data.getListStudy().add(new Word("desk", "b??n h???c", R.drawable.desk));
        data.getListStudy().add(new Word("cutter", "dao r???c gi???y", R.drawable.cutter));
        data.getListStudy().add(new Word("crayon", "b??t s??p m??u", R.drawable.crayon));
        data.getListStudy().add(new Word("computer", "m??y t??nh b??n", R.drawable.computer));
        data.getListStudy().add(new Word("clock", "?????ng h???", R.drawable.clock));
        data.getListStudy().add(new Word("clamp", "c??i k???p", R.drawable.clamp));
        data.getListStudy().add(new Word("chalk", "ph???n vi???t", R.drawable.chalk));
        data.getListStudy().add(new Word("chair", "gh??? t???a", R.drawable.chair));
        data.getListStudy().add(new Word("bookshelf", "gi?? ????? s??ch", R.drawable.bookshelf));
        data.getListStudy().add(new Word("board", "b???ng", R.drawable.board));
        data.getListStudy().add(new Word("book", "s??ch, v???", R.drawable.book));
        data.getListStudy().add(new Word("bag", "c???p s??ch", R.drawable.bag));
        data.getListStudy().add(new Word("scissors", "c??i k??o", R.drawable.scissors));
        data.getListStudy().add(new Word("protractor", "th?????c ??o g??c", R.drawable.protractor));
        data.getListStudy().add(new Word("stapler", "ghim b???m", R.drawable.stapler));
        data.getListStudy().add(new Word("timetable", "th???i kh??a bi???u", R.drawable.timetable));
        data.getListStudy().add(new Word("calculator", "m??y t??nh b??? t??i", R.drawable.calculator));

        //christmas
        data.getListChristmas().add(new Word("reindeer","tu???n l???c", R.drawable.reindeer));
        data.getListChristmas().add(new Word("present","qu?? t???ng", R.drawable.present));
        data.getListChristmas().add(new Word("sled","xe tr?????t tuy???t", R.drawable.sled));
        data.getListChristmas().add(new Word("snowman","ng?????i tuy???t", R.drawable.snowman));
        data.getListChristmas().add(new Word("fireplace","l?? s?????i", R.drawable.firewood));
        data.getListChristmas().add(new Word("cookie","b??nh quy", R.drawable.cookie));
        data.getListChristmas().add(new Word("card","thi???p", R.drawable.card));
        data.getListChristmas().add(new Word("candle","n???n", R.drawable.candle));
        data.getListChristmas().add(new Word("ribbon","ruy b??ng", R.drawable.ribbon));
        data.getListChristmas().add(new Word("tinsel","d??y kim tuy???n", R.drawable.tinsel));
        data.getListChristmas().add(new Word("bell","chu??ng", R.drawable.bell));
        data.getListChristmas().add(new Word("wreath","v??ng hoa", R.drawable.wreath));
        data.getListChristmas().add(new Word("snowflake","b??ng tuy???t", R.drawable.snowflake));
        data.getListChristmas().add(new Word("candy","k???o", R.drawable.candy));
        data.getListChristmas().add(new Word("firewood","c???i kh??", R.drawable.firewood));
        data.getListChristmas().add(new Word("bauble","qu??? ????n", R.drawable.bauble));
        data.getListChristmas().add(new Word("christmas tree","aaa", R.drawable.christmas_tree));
        data.getListChristmas().add(new Word("Santa Claus","??ng gi?? Noel", R.drawable.santa_claus));

        //place
        data.getListPlace().add(new Word("bank", "ng??n h??ng", R.drawable.bank));
        data.getListPlace().add(new Word("cinema", "r???p phim", R.drawable.cinema));
        data.getListPlace().add(new Word("factory", "nh?? m??y", R.drawable.factory));
        data.getListPlace().add(new Word("hotel", "kh??ch s???n", R.drawable.hotel));
        data.getListPlace().add(new Word("library", "th?? vi???n", R.drawable.library));
        data.getListPlace().add(new Word("market", "ch???", R.drawable.market));
        data.getListPlace().add(new Word("bridge", "c??y c???u", R.drawable.bridge));
        data.getListPlace().add(new Word("beach", "b??i bi???n", R.drawable.beach));
        data.getListPlace().add(new Word("church", "nh?? th???", R.drawable.church));
        data.getListPlace().add(new Word("island", "h??n ?????o", R.drawable.island));
        data.getListPlace().add(new Word("lake", "c??i h???", R.drawable.lake));
        data.getListPlace().add(new Word("river", "con s??ng", R.drawable.river));
        data.getListPlace().add(new Word("park", "c??ng vi??n", R.drawable.park));
        data.getListPlace().add(new Word("pagoda", "ng??i ch??a", R.drawable.pagoda));
        data.getListPlace().add(new Word("school", "tr?????ng h???c", R.drawable.school));
        data.getListPlace().add(new Word("supermarket", "si??u th???", R.drawable.supermarket));
        data.getListPlace().add(new Word("stadium", "s??n v???n ?????ng", R.drawable.stadium));
        data.getListPlace().add(new Word("restaurant", "nh?? h??ng", R.drawable.restaurant));
        data.getListPlace().add(new Word("mountain", "ng???n n??i", R.drawable.mountain));
        data.getListPlace().add(new Word("museum", "b???o t??ng", R.drawable.museum));
        data.getListPlace().add(new Word("hospital", "b???nh vi???n", R.drawable.hospital));
        data.getListPlace().add(new Word("bakery", "c???a h??ng b??nh", R.drawable.bakery));

    }


/*
    @Override
    protected void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        MyData.soundBackground.start();
        super.onRestart();
    }*/
}
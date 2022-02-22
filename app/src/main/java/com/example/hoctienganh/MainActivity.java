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
            builder.setTitle("Thông báo!");
            builder.setMessage("Đăng nhập để tiếp tục!");
            builder.setCancelable(false);

            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentLogin = new Intent(MainActivity.this, ActivityLogin.class);
                    startActivity(intentLogin);
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
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
        data.getListAnimal().add(new Word("dog","con chó", R.drawable.dog));
        data.getListAnimal().add(new Word("cat","con mèo", R.drawable.cat));
        data.getListAnimal().add(new Word("pig","con lợn", R.drawable.pig));
        data.getListAnimal().add(new Word("duck","con vịt", R.drawable.duck));
        data.getListAnimal().add(new Word("goose","con ngỗng", R.drawable.goose));
        data.getListAnimal().add(new Word("chicken","con gà", R.drawable.chicken));
        data.getListAnimal().add(new Word("rabbit","con thỏ", R.drawable.rabbit));
        data.getListAnimal().add(new Word("buffalo","con trâu", R.drawable.buffalo));
        data.getListAnimal().add(new Word("horse","con ngựa", R.drawable.horse));
        data.getListAnimal().add(new Word("donkey","con lừa", R.drawable.donkey));
        data.getListAnimal().add(new Word("sheep","con cừu", R.drawable.sheep));
        data.getListAnimal().add(new Word("goat","con dê", R.drawable.goat));
        data.getListAnimal().add(new Word("cow","con bò", R.drawable.cow));
        data.getListAnimal().add(new Word("mouse","con chuột", R.drawable.mouse));
        data.getListAnimal().add(new Word("monkey","con khỉ", R.drawable.monkey));
        data.getListAnimal().add(new Word("lion","con sư tử", R.drawable.lion));
        data.getListAnimal().add(new Word("tiger","con hổ", R.drawable.tiger));
        data.getListAnimal().add(new Word("elephant","con voi", R.drawable.elephant));
        data.getListAnimal().add(new Word("fox","con cáo", R.drawable.fox));
        data.getListAnimal().add(new Word("bear","con gấu", R.drawable.bear));
        data.getListAnimal().add(new Word("giraffe","hươu cao cổ", R.drawable.giraffe));
        data.getListAnimal().add(new Word("hippopotamus","con hà mã", R.drawable.hippopotamus));
        data.getListAnimal().add(new Word("jaguar","con báo", R.drawable.jaguar));
        data.getListAnimal().add(new Word("porcupine","con nhím", R.drawable.porcupine));
        data.getListAnimal().add(new Word("rhinoceros","con tế giác", R.drawable.rhinoceros));
        data.getListAnimal().add(new Word("squirrel","con sóc", R.drawable.squirrel));
        data.getListAnimal().add(new Word("crocodile","con cá sấu", R.drawable.crocodile));
        data.getListAnimal().add(new Word("bat","con dơi", R.drawable.bat));
        data.getListAnimal().add(new Word("camel","con lạc đà", R.drawable.camel));
        data.getListAnimal().add(new Word("kangaroo","con chuột túi", R.drawable.kangaroo));
        data.getListAnimal().add(new Word("panda","con gấu trúc", R.drawable.panda));
        data.getListAnimal().add(new Word("deer","con hươu", R.drawable.deer));
        data.getListAnimal().add(new Word("wolf","con sói", R.drawable.wolf));
        data.getListAnimal().add(new Word("hedgehog","con nhím", R.drawable.hedgehog));
        data.getListAnimal().add(new Word("zebra","con ngựa vằn", R.drawable.zebra));
        data.getListAnimal().add(new Word("penguin","chim cánh cụt", R.drawable.penguin));
        data.getListAnimal().add(new Word("dolphin","cá voi", R.drawable.dolphin));
        data.getListAnimal().add(new Word("shark","cá mập", R.drawable.shark));
        data.getListAnimal().add(new Word("whale","cá voi", R.drawable.whale));
        data.getListAnimal().add(new Word("turtle","con rùa", R.drawable.turtle));
        data.getListAnimal().add(new Word("jellyfish","con sứa", R.drawable.jellyfish));
        data.getListAnimal().add(new Word("octopus","bạch tuộc", R.drawable.octopus));
        data.getListAnimal().add(new Word("squid","con mực", R.drawable.squid));
        data.getListAnimal().add(new Word("starfish","sao biển", R.drawable.starfish));
        data.getListAnimal().add(new Word("clownfish","cá hề", R.drawable.clownfish));
        data.getListAnimal().add(new Word("crab","con cua", R.drawable.crab));
        data.getListAnimal().add(new Word("seahorse","cá ngựa", R.drawable.seahorse));
        data.getListAnimal().add(new Word("seal","hải cẩu", R.drawable.seal));
        data.getListAnimal().add(new Word("owl","con cú", R.drawable.owl));
        data.getListAnimal().add(new Word("turkey","gà tây", R.drawable.turkey));
        data.getListAnimal().add(new Word("parrot","con vẹt", R.drawable.parrot));
        data.getListAnimal().add(new Word("ostrich","đà điểu", R.drawable.ostrich));
        data.getListAnimal().add(new Word("pigeon","bồ câu", R.drawable.pigeon));
        data.getListAnimal().add(new Word("eagle","đại bàng", R.drawable.eagle));
        data.getListAnimal().add(new Word("crow","con quạ", R.drawable.crow));
        data.getListAnimal().add(new Word("sparrow","chim sẻ", R.drawable.sparrow));
        data.getListAnimal().add(new Word("fly","con ruồi", R.drawable.fly));
        data.getListAnimal().add(new Word("mosquito","con muỗi", R.drawable.mosquito));
        data.getListAnimal().add(new Word("butterfly","con bướm", R.drawable.butterfly));
        data.getListAnimal().add(new Word("dragonfly","chuồn chuồn", R.drawable.dragonfly));
        data.getListAnimal().add(new Word("cockroach","con gián", R.drawable.cockroach));
        data.getListAnimal().add(new Word("ant","con kiến", R.drawable.ant));

        //color

        data.getListColor().add(new Word("black","màu đen",R.drawable.blackcolor));
        data.getListColor().add(new Word("blue","màu xanh dương",R.drawable.bluecolor));
        data.getListColor().add(new Word("gray","màu xám",R.drawable.graycolor));
        data.getListColor().add(new Word("brown","màu nâu",R.drawable.browncolor));
        data.getListColor().add(new Word("green","màu xanh lá",R.drawable.greencolor));
        data.getListColor().add(new Word("orange","màu cam",R.drawable.orangecolor));
        data.getListColor().add(new Word("pink","màu hồng",R.drawable.pinkcolor));
        data.getListColor().add(new Word("purple","màu tím",R.drawable.purplecolor));
        data.getListColor().add(new Word("red","màu đỏ",R.drawable.redcolor));
        data.getListColor().add(new Word("white","màu trắng",R.drawable.whitecolor));
        data.getListColor().add(new Word("yellow","màu vàng",R.drawable.yellocolor));


        //clothe
        data.getListClothes().add(new Word("dress", "chiếc váy", R.drawable.dress));
        data.getListClothes().add(new Word("pants", "quần tây", R.drawable.pants));
        data.getListClothes().add(new Word("short", "quần đùi", R.drawable.shorts));
        data.getListClothes().add(new Word("shirt", "áo sơ mi", R.drawable.shirt));
        data.getListClothes().add(new Word("T-shirt", "áo thun", R.drawable.tshirt));
        data.getListClothes().add(new Word("suit", "bộ đồ vest", R.drawable.suit));
        data.getListClothes().add(new Word("jacket", "áo khoác", R.drawable.jacket));
        data.getListClothes().add(new Word("gloves", "găng tay", R.drawable.gloves));
        data.getListClothes().add(new Word("belt", "thắt lưng", R.drawable.belt));
        data.getListClothes().add(new Word("cap", "mũ lưỡi trai", R.drawable.cap));
        data.getListClothes().add(new Word("jeans", "quần jeans", R.drawable.jeans));
        data.getListClothes().add(new Word("scarf", "khăn quàng cổ", R.drawable.scarf));
        data.getListClothes().add(new Word("shoes", "đôi giày", R.drawable.shoes));
        data.getListClothes().add(new Word("wallet", "chiếc ví", R.drawable.wallet));
        data.getListClothes().add(new Word("socks", "đôi tất", R.drawable.socks));
        data.getListClothes().add(new Word("hat", "cái mũ", R.drawable.hat));
        data.getListClothes().add(new Word("glasses", "kính", R.drawable.glasses));
        data.getListClothes().add(new Word("handbag", "túi xách", R.drawable.handbag));
        data.getListClothes().add(new Word("skirt", "chân váy", R.drawable.skirt));
        data.getListClothes().add(new Word("sweater", "áo len", R.drawable.sweater));
        data.getListClothes().add(new Word("tie", "cà vạt", R.drawable.tie));

        //fruit
        data.getListFruit().add(new Word("apple", "quả táo", R.drawable.apple));
        data.getListFruit().add(new Word("grape", "quả nho", R.drawable.grape));
        data.getListFruit().add(new Word("banana", "quả chuối", R.drawable.banana));
        data.getListFruit().add(new Word("pear", "quả lê", R.drawable.pear));
        data.getListFruit().add(new Word("orange", "quả cam", R.drawable.orange));
        data.getListFruit().add(new Word("pineapple", "quả dứa", R.drawable.pineapple));
        data.getListFruit().add(new Word("peach", "quả đào", R.drawable.peach));
        data.getListFruit().add(new Word("dragon fruit", "quả thanh long", R.drawable.dragon_fruit));
        data.getListFruit().add(new Word("starfruit", "quả khế", R.drawable.starfruit));
        data.getListFruit().add(new Word("jackfruit", "quả mít", R.drawable.jackfruit));
        data.getListFruit().add(new Word("guava", "quả ổi", R.drawable.guava));
        data.getListFruit().add(new Word("mango", "quả xoài", R.drawable.mango));
        data.getListFruit().add(new Word("coconut", "quả dừa", R.drawable.coconut));
        data.getListFruit().add(new Word("durian", "quả sầu riêng", R.drawable.durian));
        data.getListFruit().add(new Word("avocado", "quả bơ", R.drawable.avocado));
        data.getListFruit().add(new Word("pomelo", "quả bưởi", R.drawable.pomelo));
        data.getListFruit().add(new Word("apricot", "quả mơ", R.drawable.apricot));
        data.getListFruit().add(new Word("papaya", "quả đu đủ", R.drawable.papaya));
        data.getListFruit().add(new Word("mangosteen", "quả măng cụt", R.drawable.mangosteen));
        data.getListFruit().add(new Word("rambutan", "quả chôm chôm", R.drawable.rambutan));
        data.getListFruit().add(new Word("blackberry", "quả mâm xôi", R.drawable.blackberry));
        data.getListFruit().add(new Word("stawberry", "dâu tây", R.drawable.stawberry));
        data.getListFruit().add(new Word("longan", "quả nhãn", R.drawable.longan));
        data.getListFruit().add(new Word("lychee", "quả vải", R.drawable.lychee));
        data.getListFruit().add(new Word("persimmon", "quả hồng", R.drawable.persimmon));
        data.getListFruit().add(new Word("kiwi", "quả kiwi", R.drawable.kiwi));
        data.getListFruit().add(new Word("plum", "quả mận", R.drawable.plum));
        data.getListFruit().add(new Word("watermelon", "quả dưa hấu", R.drawable.watermelon));
        data.getListFruit().add(new Word("lemon", "quả chanh", R.drawable.lemon));
        data.getListFruit().add(new Word("cherry", "quả anh đào", R.drawable.cherry));


        //body
        data.getListBody().add(new Word("eye", "mắt", R.drawable.eye));
        data.getListBody().add(new Word("nose", "mũi", R.drawable.nose));
        data.getListBody().add(new Word("mouth", "miệng", R.drawable.mouth));
        data.getListBody().add(new Word("hair", "tóc", R.drawable.hair));
        data.getListBody().add(new Word("ear", "tai", R.drawable.ear));
        data.getListBody().add(new Word("hand", "bàn tay", R.drawable.hand));
        data.getListBody().add(new Word("arm", "cánh tay", R.drawable.arm));
        data.getListBody().add(new Word("neck", "cổ", R.drawable.neck));
        data.getListBody().add(new Word("stomach", "bụng", R.drawable.stomach));
        data.getListBody().add(new Word("leg", "chân", R.drawable.leg));
        data.getListBody().add(new Word("foot", "bàn chân", R.drawable.foot));
        data.getListBody().add(new Word("forehead", "trán", R.drawable.forehead));
        data.getListBody().add(new Word("eyebrow", "lông mày", R.drawable.eyebrow));
        data.getListBody().add(new Word("tooth", "răng", R.drawable.tooth));
        data.getListBody().add(new Word("cheek", "má", R.drawable.cheek));
        data.getListBody().add(new Word("chin", "cằm", R.drawable.chin));
        data.getListBody().add(new Word("finger", "ngón tay", R.drawable.finger));
        data.getListBody().add(new Word("elbow", "khuỷu tay", R.drawable.elbow));
        data.getListBody().add(new Word("knee", "đầu gối", R.drawable.knee));
        data.getListBody().add(new Word("shoulder", "vai", R.drawable.shoulder));

        //country
        data.getListCountry().add(new Word("Denmark", "Đan Mạch", R.drawable.danmark));
        data.getListCountry().add(new Word("England", "Anh Quốc", R.drawable.england));
        data.getListCountry().add(new Word("Finland", "Phần Lan", R.drawable.finland));
        data.getListCountry().add(new Word("Sweden", "Thụy Điển", R.drawable.sweden));
        data.getListCountry().add(new Word("Austria", "Áo", R.drawable.austria));
        data.getListCountry().add(new Word("France", "Pháp", R.drawable.france));
        data.getListCountry().add(new Word("Belgium", "Bỉ", R.drawable.belgium));
        data.getListCountry().add(new Word("Germany", "Đức", R.drawable.germany));
        data.getListCountry().add(new Word("Netherlands", "Hà Lan", R.drawable.netherlands));
        data.getListCountry().add(new Word("Switzerland", "Thụy Sỹ", R.drawable.switzerland));
        data.getListCountry().add(new Word("Italy", "Ý", R.drawable.italy));
        data.getListCountry().add(new Word("Greece", "Hi Lạp", R.drawable.greece));
        data.getListCountry().add(new Word("Portugal", "Bồ Đào Nha", R.drawable.portugal));
        data.getListCountry().add(new Word("Spain", "Tây Ban Nha", R.drawable.spain));
        data.getListCountry().add(new Word("Russia", "Nga", R.drawable.russia));
        data.getListCountry().add(new Word("Canada", "Ca-na-da", R.drawable.canada));
        data.getListCountry().add(new Word("United States", "Hoa Kỳ", R.drawable.united_states));
        data.getListCountry().add(new Word("Brazil", "Bra-xin", R.drawable.brazil));
        data.getListCountry().add(new Word("Argentina", "Ác-hen-ti-na", R.drawable.argentina));
        data.getListCountry().add(new Word("India", "Ấn Độ", R.drawable.india));
        data.getListCountry().add(new Word("China", "Trung Quốc", R.drawable.china));
        data.getListCountry().add(new Word("Japan", "Nhật Bản", R.drawable.japan));
        data.getListCountry().add(new Word("North Korea", "Triều Tiên", R.drawable.north_korea));
        data.getListCountry().add(new Word("South Korea", "Hàn Quốc", R.drawable.south_korea));
        data.getListCountry().add(new Word("Cambodia", "Cam-pu-chia", R.drawable.cambodia));
        data.getListCountry().add(new Word("Indonesia", "In-đô-nê-si-a", R.drawable.indonesia));
        data.getListCountry().add(new Word("Laos", "Lào", R.drawable.laos));
        data.getListCountry().add(new Word("Malaysia", "Ma-lai-si-a", R.drawable.malaysia));
        data.getListCountry().add(new Word("Myanmar", "Mi-an-ma", R.drawable.myanmar));
        data.getListCountry().add(new Word("Philippines", "Phi-líp-pin", R.drawable.philippines));
        data.getListCountry().add(new Word("Singapore", "Sinh-ga-po", R.drawable.singapore));
        data.getListCountry().add(new Word("Thailand", "Thái Lan", R.drawable.thailand));
        data.getListCountry().add(new Word("Vietnam", "Việt Nam", R.drawable.vietnam));
        data.getListCountry().add(new Word("Australia", "Úc", R.drawable.australia));
        data.getListCountry().add(new Word("Egypt", "Ai Cập", R.drawable.egypt));
        data.getListCountry().add(new Word("South Africa", "Nam Phi", R.drawable.south_africa));

        //drink
        data.getListDrink().add(new Word("coffee", "cà phê", R.drawable.coffee));
        data.getListDrink().add(new Word("beer", "bia", R.drawable.beer));
        data.getListDrink().add(new Word("wine", "rượu", R.drawable.wine));
        data.getListDrink().add(new Word("juice", "nước ép trái cây", R.drawable.juice));
        data.getListDrink().add(new Word("tea", "trà", R.drawable.tea));
        data.getListDrink().add(new Word("soft drink", "nước ngọt", R.drawable.soft_drink));
        data.getListDrink().add(new Word("mineral water", "nước khoáng", R.drawable.mineral_water));
        data.getListDrink().add(new Word("cocktail", "cốc-tai", R.drawable.cocktail));
        data.getListDrink().add(new Word("lemonade", "nước chanh", R.drawable.lemonade));

        //flower
        data.getListFlower().add((new Word("daisy", "hoa cúc", R.drawable.daisy)));
        data.getListFlower().add((new Word("rose", "hoa hồng", R.drawable.rose)));
        data.getListFlower().add((new Word("orchid", "hoa lan", R.drawable.orchid)));
        data.getListFlower().add((new Word("tulip", "hoa tu-lip", R.drawable.tulip)));
        data.getListFlower().add((new Word("sunflower", "hoa hướng dương", R.drawable.sunflower)));
        data.getListFlower().add((new Word("lily", "hoa loa kèn", R.drawable.lily)));
        data.getListFlower().add((new Word("carnation", "hoa cẩm chướng", R.drawable.carnation)));
        data.getListFlower().add((new Word("mimosa", "hoa xấu hổ", R.drawable.mimosa)));
        data.getListFlower().add((new Word("gladiolus", "hoa lay-ơn", R.drawable.gladiolus)));
        data.getListFlower().add((new Word("lotus", "hoa sen", R.drawable.lotus)));
        data.getListFlower().add((new Word("sakura", "hoa anh đào", R.drawable.sakura)));
        data.getListFlower().add((new Word("water lily", "hoa súng", R.drawable.water_lily)));
        data.getListFlower().add((new Word("peony", "hoa mẫu đơn", R.drawable.peony)));
        data.getListFlower().add((new Word("dandelion", "bồ công anh", R.drawable.dandelion)));
        data.getListFlower().add((new Word("tuberose", "hoa huệ", R.drawable.tuberose)));
        data.getListFlower().add((new Word("hibiscus", "hoa dâm bụt", R.drawable.hibiscus)));
        data.getListFlower().add((new Word("gerbera", "hoa đồng tiền", R.drawable.gerbera)));
        data.getListFlower().add((new Word("flamboyant", "hoa phượng", R.drawable.flamboyant)));

        //food

        data.getListFood().add(new Word("bread", "bánh mì", R.drawable.bread));
        data.getListFood().add(new Word("egg", "trứng", R.drawable.egg));
        data.getListFood().add(new Word("cheese", "phô mai", R.drawable.cheese));
        data.getListFood().add(new Word("rice", "cơm", R.drawable.rice));
        data.getListFood().add(new Word("pizza", "bánh pizza", R.drawable.pizza));
        data.getListFood().add(new Word("yogurt", "sữa chua", R.drawable.yogurt));
        data.getListFood().add(new Word("cake", "bánh ngọt", R.drawable.cake));
        data.getListFood().add(new Word("chicken", "thịt gà", R.drawable.chickenfood));
        data.getListFood().add(new Word("pork", "thịt lợn", R.drawable.pork));
        data.getListFood().add(new Word("beef", "thịt bò", R.drawable.beef));
        data.getListFood().add(new Word("fish", "cá", R.drawable.fish));
        data.getListFood().add(new Word("shrimp", "tôm", R.drawable.shrimp));
        data.getListFood().add(new Word("lobster", "tôm hùm", R.drawable.lobster));
        data.getListFood().add(new Word("clam", "nghêu", R.drawable.clam));
        data.getListFood().add(new Word("mussel", "con trai", R.drawable.mussel));
        data.getListFood().add(new Word("sausage", "xúc xích", R.drawable.sausage));
        data.getListFood().add(new Word("steak", "bít tết", R.drawable.steak));
        data.getListFood().add(new Word("salt", "muối", R.drawable.salt));
        data.getListFood().add(new Word("sugar", "đường", R.drawable.sugar));
        data.getListFood().add(new Word("fish sauce", "nước mắm", R.drawable.fish_sauce));
        data.getListFood().add(new Word("soy sauce", "nước tương", R.drawable.soy_sauce));
        data.getListFood().add(new Word("vinegar", "dấm", R.drawable.vinegar));
        //data.getListFood().add(new Word("noodle", "mỳ gói", R.drawable.));

        //house
        data.getListHouse().add(new Word("bathroom", "nhà tắm", R.drawable.bathroom));
        data.getListHouse().add(new Word("bedroom", "phòng ngủ", R.drawable.bedroom));
        data.getListHouse().add(new Word("kitchen", "bếp", R.drawable.kitchen));
        data.getListHouse().add(new Word("living room", "phòng khách", R.drawable.living_room));
        data.getListHouse().add(new Word("garden", "vườn", R.drawable.garden));
        data.getListHouse().add(new Word("bed", "cái giường", R.drawable.bed));
        data.getListHouse().add(new Word("fan", "cái quạt", R.drawable.fan));
        data.getListHouse().add(new Word("picture", "bức tranh", R.drawable.picture));
        data.getListHouse().add(new Word("pillow", "cái gối", R.drawable.pillow));
        data.getListHouse().add(new Word("blanket", "chăn", R.drawable.blanket));
        data.getListHouse().add(new Word("mattress", "nệm", R.drawable.mattress));
        data.getListHouse().add(new Word("bin", "thùng rác", R.drawable.bin));
        data.getListHouse().add(new Word("television", "ti vi", R.drawable.television));
        data.getListHouse().add(new Word("telephone", "điện thoại bàn", R.drawable.telephone));
        data.getListHouse().add(new Word("air conditioner", "điều hòa", R.drawable.air_conditioner));
        data.getListHouse().add(new Word("toilet", "bồn cầu", R.drawable.toilet));
        data.getListHouse().add(new Word("washing machine", "máy giặt", R.drawable.washing_machine));
        data.getListHouse().add(new Word("sink", "bồn rửa", R.drawable.sink));
        data.getListHouse().add(new Word("shower", "vòi hoa sen", R.drawable.shower));
        data.getListHouse().add(new Word("tub", "bồn tắm", R.drawable.tub));
        data.getListHouse().add(new Word("toothpaste", "kem đánh răng", R.drawable.toothpaste));
        data.getListHouse().add(new Word("toothbrush", "bàn chải đánh răng", R.drawable.toothbrush));
        data.getListHouse().add(new Word("mirror", "cái gương", R.drawable.mirror));
        data.getListHouse().add(new Word("razor", "dao cạo râu", R.drawable.razor));
        data.getListHouse().add(new Word("shampoo", "dầu gội", R.drawable.shampoo));
        data.getListHouse().add(new Word("table", "bàn", R.drawable.table));
        data.getListHouse().add(new Word("bench", "ghế bành", R.drawable.bench));
        data.getListHouse().add(new Word("sofa", "ghế sô-pha", R.drawable.sofa));
        data.getListHouse().add(new Word("vase", "lọ", R.drawable.vase));
        data.getListHouse().add(new Word("fridge", "tủ lạnh", R.drawable.fridge));
        data.getListHouse().add(new Word("cooker", "nồi cơm điện", R.drawable.cooker));
        data.getListHouse().add(new Word("dishwasher", "máy rửa bát", R.drawable.dishwasher));
        data.getListHouse().add(new Word("microwave", "lò vi sóng", R.drawable.microwave));
        data.getListHouse().add(new Word("apron", "tạp dề", R.drawable.apron));
        data.getListHouse().add(new Word("saucepan", "cái nồi", R.drawable.saucepan));
        data.getListHouse().add(new Word("pan", "cái chảo", R.drawable.pan));
        data.getListHouse().add(new Word("colander", "cái rổ", R.drawable.colander));
        data.getListHouse().add(new Word("chopsticks", "đũa", R.drawable.chopsticks));
        data.getListHouse().add(new Word("spoon", "cái thìa", R.drawable.spoon));
        data.getListHouse().add(new Word("fork", "cái nĩa", R.drawable.fork));
        data.getListHouse().add(new Word("bowl", "bát", R.drawable.bowl));
        data.getListHouse().add(new Word("calendar", "lịch", R.drawable.calendar));
        data.getListHouse().add(new Word("comb", "cái lược", R.drawable.comb));
        data.getListHouse().add(new Word("lamp", "đèn bàn", R.drawable.lamp));
        data.getListHouse().add(new Word("cup", "cái cốc", R.drawable.cup));
        data.getListHouse().add(new Word("knife", "con dao", R.drawable.knife));
        data.getListHouse().add(new Word("floor", "sàn nhà", R.drawable.floor));
        data.getListHouse().add(new Word("roof", "mái nhà", R.drawable.roof));
        data.getListHouse().add(new Word("chimney", "ống khói", R.drawable.chimney));

        //job
        data.getListJob().add(new Word("doctor", "bác sỹ", R.drawable.doctor));
        data.getListJob().add(new Word("dentist", "nha sỹ", R.drawable.dentist));
        data.getListJob().add(new Word("nurse", "y tá", R.drawable.nurse));
        data.getListJob().add(new Word("teacher", "giáo viên", R.drawable.teacher));
        data.getListJob().add(new Word("reporter", "phóng viên", R.drawable.reporter));
        data.getListJob().add(new Word("chef", "đầu bếp", R.drawable.chef));
        data.getListJob().add(new Word("magician", "ảo thuật gia", R.drawable.magician));
        data.getListJob().add(new Word("baker", "thợ làm bánh", R.drawable.baker));
        data.getListJob().add(new Word("singer", "ca sỹ", R.drawable.singer));
        data.getListJob().add(new Word("artist", "họa sỹ", R.drawable.artist));
        data.getListJob().add(new Word("lawyer", "luật sư", R.drawable.lawyer));
        data.getListJob().add(new Word("policeman", "cảnh sát", R.drawable.policeman));
        data.getListJob().add(new Word("hairdresser", "thợ cắt tóc", R.drawable.hairdresser));
        data.getListJob().add(new Word("dancer", "vũ công", R.drawable.dancer));
        data.getListJob().add(new Word("farmer", "nông dân", R.drawable.farmer));
        data.getListJob().add(new Word("pilot", "phi công", R.drawable.pilot));
        data.getListJob().add(new Word("photographer", "nhiếp ảnh gia", R.drawable.photographer));
        data.getListJob().add(new Word("sailor", "thủy thủ", R.drawable.sailor));
        data.getListJob().add(new Word("firefighter", "lính cứu hỏa", R.drawable.firefighter));
        data.getListJob().add(new Word("astronaut", "phi hành gia", R.drawable.astronaut));
        data.getListJob().add(new Word("architect", "kiến trúc sư", R.drawable.architect));

        //sport
        data.getListSport().add(new Word("cycling", "đạp xe", R.drawable.cycling));
        data.getListSport().add(new Word("tennis", "quần vợt", R.drawable.tennis));
        data.getListSport().add(new Word("running", "chạy bộ", R.drawable.running));
        data.getListSport().add(new Word("swimming", "bơi lội", R.drawable.swimming));
        data.getListSport().add(new Word("riding", "cưỡi ngựa", R.drawable.riding));
        data.getListSport().add(new Word("volleyball", "bóng chuyền", R.drawable.volleyball));
        data.getListSport().add(new Word("football", "bóng đá", R.drawable.football));
        data.getListSport().add(new Word("basketball", "bóng rổ", R.drawable.basketball));
        data.getListSport().add(new Word("table tennis", "bóng bàn", R.drawable.table_tennis));
        data.getListSport().add(new Word("baseball", "bóng chày", R.drawable.baseball));
        data.getListSport().add(new Word("golf", "đánh golf", R.drawable.golf));
        data.getListSport().add(new Word("badminton", "cầu lông", R.drawable.badminton));
        data.getListSport().add(new Word("windsurfing", "lướt ván buồm", R.drawable.windsufting));
        data.getListSport().add(new Word("skiing", "trượt tuyết", R.drawable.skiing));
        data.getListSport().add(new Word("skateboarding", "trượt ván", R.drawable.skateboarding));

        //vehicle
        data.getListVehicle().add(new Word("car", "xe hơi", R.drawable.car));
        data.getListVehicle().add(new Word("truck", "xe tải", R.drawable.truck));
        data.getListVehicle().add(new Word("bus", "xe buýt", R.drawable.bus));
        data.getListVehicle().add(new Word("bicycle", "xe đạp", R.drawable.bicycle));
        data.getListVehicle().add(new Word("motorbike", "xe máy", R.drawable.motorbike));
        data.getListVehicle().add(new Word("train", "tàu hỏa", R.drawable.train));
        //data.getListVehicle().add(new Word("subway", "tàu điện ngầm", R.drawable.s));
        data.getListVehicle().add(new Word("helicopter", "trực thăng", R.drawable.helicopter));
        data.getListVehicle().add(new Word("plane", "máy bay", R.drawable.plane));
        data.getListVehicle().add(new Word("balloon", "khinh khí cầu", R.drawable.balloon));
        data.getListVehicle().add(new Word("ship", "tàu biển", R.drawable.ship));
        data.getListVehicle().add(new Word("submarine", "tàu ngầm", R.drawable.submarine));
        data.getListVehicle().add(new Word("sailboat", "thuyền buồm", R.drawable.sailboat));
        data.getListVehicle().add(new Word("tractor", "máy kéo", R.drawable.tractor));
        data.getListVehicle().add(new Word("bulldozer", "máy ủi", R.drawable.bulldozer));
        data.getListVehicle().add(new Word("excavator", "máy xúc", R.drawable.excavator));

        //vegetable
        data.getListVegetable().add(new Word("tomato", "cà chua", R.drawable.tomato));
        data.getListVegetable().add(new Word("carrot", "cà rốt", R.drawable.carrot));
        data.getListVegetable().add(new Word("onion", "hành tây", R.drawable.onion));
        data.getListVegetable().add(new Word("cucumber", "dưa chuột", R.drawable.cucumber));
        data.getListVegetable().add(new Word("cauliflower", "súp lơ", R.drawable.cauliflower));
        data.getListVegetable().add(new Word("eggplant", "cà tím", R.drawable.eggplant));
        data.getListVegetable().add(new Word("pepper", "hạt tiêu", R.drawable.pepper));
        data.getListVegetable().add(new Word("chilli", "trái ớt", R.drawable.chilli));
        data.getListVegetable().add(new Word("pumpkin", "pumpkin", R.drawable.pumpkin));
        data.getListVegetable().add(new Word("sweet potato", "khoai lang", R.drawable.sweet_potato));
        data.getListVegetable().add(new Word("potato", "khoai tây", R.drawable.potato));
        data.getListVegetable().add(new Word("cabbage", "bắp cải", R.drawable.cabbage));
        data.getListVegetable().add(new Word("garlic", "tỏi", R.drawable.garlic));
        data.getListVegetable().add(new Word("beetroot", "củ dền", R.drawable.beetroot));
        data.getListVegetable().add(new Word("peas", "đậu Hà Lan", R.drawable.peas));
        data.getListVegetable().add(new Word("corn", "bắp ngô", R.drawable.corn));
        data.getListVegetable().add(new Word("spinach", "rau chân vịt", R.drawable.spinach));
        data.getListVegetable().add(new Word("ginger", "củ gừng", R.drawable.ginger));
        data.getListVegetable().add(new Word("shallot", "hành tím", R.drawable.shallot));
        data.getListVegetable().add(new Word("peanut", "hạt lạc", R.drawable.peanut));
        data.getListVegetable().add(new Word("lemongrass", "sả", R.drawable.lemongrass));
        data.getListVegetable().add(new Word("broccoli", "bông cải xanh", R.drawable.broccoli));
        data.getListVegetable().add(new Word("asparagus", "măng tây", R.drawable.asparagus));
        data.getListVegetable().add(new Word("turmeric", "củ nghệ", R.drawable.turmeric));
        data.getListVegetable().add(new Word("kohlrabi", "su hào", R.drawable.kohlrabi));
        data.getListVegetable().add(new Word("seaweed", "rong biển", R.drawable.seaweed));
        data.getListVegetable().add(new Word("mushroom", "nấm", R.drawable.mushroom));

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
        data.getListNumber().add(new Word("zero", "số không", R.drawable.number00));
        data.getListNumber().add(new Word("one", "số một", R.drawable.number01));
        data.getListNumber().add(new Word("two", "số hai", R.drawable.number02));
        data.getListNumber().add(new Word("three", "số ba", R.drawable.number03));
        data.getListNumber().add(new Word("four", "số bốn", R.drawable.number04));
        data.getListNumber().add(new Word("five", "số năm", R.drawable.number05));
        data.getListNumber().add(new Word("six", "số sáu", R.drawable.number06));
        data.getListNumber().add(new Word("seven", "số bảy", R.drawable.number07));
        data.getListNumber().add(new Word("eight", "số tám", R.drawable.number08));
        data.getListNumber().add(new Word("nine", "số chín", R.drawable.number09));
        data.getListNumber().add(new Word("ten", "số mười", R.drawable.number10));
        data.getListNumber().add(new Word("eleven", "số mười một", R.drawable.number11));
        data.getListNumber().add(new Word("twelve", "số mười hai", R.drawable.number12));
        data.getListNumber().add(new Word("thirteen", "số mười ba", R.drawable.number13));
        data.getListNumber().add(new Word("fourteen", "số mười bốn", R.drawable.number14));
        data.getListNumber().add(new Word("fifteen", "số mười lăm", R.drawable.number15));
        data.getListNumber().add(new Word("sixteen", "số mười sáu", R.drawable.number16));
        data.getListNumber().add(new Word("seventeen", "số mười bảy", R.drawable.number17));
        data.getListNumber().add(new Word("eighteen", "số mười tám", R.drawable.number18));
        data.getListNumber().add(new Word("nineteen", "số mười chín", R.drawable.number19));
        data.getListNumber().add(new Word("twenty", "số hai mươi", R.drawable.number20));

        //study
        data.getListStudy().add(new Word("ruler", "thước kẻ", R.drawable.ruler));
        data.getListStudy().add(new Word("pen", "bút mực", R.drawable.pen));
        data.getListStudy().add(new Word("pencil", "bút chì", R.drawable.pencil));
        data.getListStudy().add(new Word("paper", "giấy viết", R.drawable.paper));
        data.getListStudy().add(new Word("marker", "bút lông", R.drawable.marker));
        data.getListStudy().add(new Word("map", "bản đồ", R.drawable.map));
        data.getListStudy().add(new Word("highlighter", "bút đánh dấu", R.drawable.highlighter));
        data.getListStudy().add(new Word("glue", "keo dán hồ", R.drawable.glue));
        data.getListStudy().add(new Word("globe", "quả địa cầu", R.drawable.globe));
        data.getListStudy().add(new Word("eraser", "cục tẩy", R.drawable.eraser));
        data.getListStudy().add(new Word("dictionary", "từ điển", R.drawable.dictionary));
        data.getListStudy().add(new Word("desk", "bàn học", R.drawable.desk));
        data.getListStudy().add(new Word("cutter", "dao rọc giấy", R.drawable.cutter));
        data.getListStudy().add(new Word("crayon", "bút sáp màu", R.drawable.crayon));
        data.getListStudy().add(new Word("computer", "máy tính bàn", R.drawable.computer));
        data.getListStudy().add(new Word("clock", "đồng hồ", R.drawable.clock));
        data.getListStudy().add(new Word("clamp", "cái kẹp", R.drawable.clamp));
        data.getListStudy().add(new Word("chalk", "phấn viết", R.drawable.chalk));
        data.getListStudy().add(new Word("chair", "ghế tựa", R.drawable.chair));
        data.getListStudy().add(new Word("bookshelf", "giá để sách", R.drawable.bookshelf));
        data.getListStudy().add(new Word("board", "bảng", R.drawable.board));
        data.getListStudy().add(new Word("book", "sách, vở", R.drawable.book));
        data.getListStudy().add(new Word("bag", "cặp sách", R.drawable.bag));
        data.getListStudy().add(new Word("scissors", "cái kéo", R.drawable.scissors));
        data.getListStudy().add(new Word("protractor", "thước đo góc", R.drawable.protractor));
        data.getListStudy().add(new Word("stapler", "ghim bấm", R.drawable.stapler));
        data.getListStudy().add(new Word("timetable", "thời khóa biểu", R.drawable.timetable));
        data.getListStudy().add(new Word("calculator", "máy tính bỏ túi", R.drawable.calculator));

        //christmas
        data.getListChristmas().add(new Word("reindeer","tuần lộc", R.drawable.reindeer));
        data.getListChristmas().add(new Word("present","quà tặng", R.drawable.present));
        data.getListChristmas().add(new Word("sled","xe trượt tuyết", R.drawable.sled));
        data.getListChristmas().add(new Word("snowman","người tuyết", R.drawable.snowman));
        data.getListChristmas().add(new Word("fireplace","lò sưởi", R.drawable.firewood));
        data.getListChristmas().add(new Word("cookie","bánh quy", R.drawable.cookie));
        data.getListChristmas().add(new Word("card","thiệp", R.drawable.card));
        data.getListChristmas().add(new Word("candle","nến", R.drawable.candle));
        data.getListChristmas().add(new Word("ribbon","ruy băng", R.drawable.ribbon));
        data.getListChristmas().add(new Word("tinsel","dây kim tuyến", R.drawable.tinsel));
        data.getListChristmas().add(new Word("bell","chuông", R.drawable.bell));
        data.getListChristmas().add(new Word("wreath","vòng hoa", R.drawable.wreath));
        data.getListChristmas().add(new Word("snowflake","bông tuyết", R.drawable.snowflake));
        data.getListChristmas().add(new Word("candy","kẹo", R.drawable.candy));
        data.getListChristmas().add(new Word("firewood","củi khô", R.drawable.firewood));
        data.getListChristmas().add(new Word("bauble","quả đèn", R.drawable.bauble));
        data.getListChristmas().add(new Word("christmas tree","aaa", R.drawable.christmas_tree));
        data.getListChristmas().add(new Word("Santa Claus","Ông già Noel", R.drawable.santa_claus));

        //place
        data.getListPlace().add(new Word("bank", "ngân hàng", R.drawable.bank));
        data.getListPlace().add(new Word("cinema", "rạp phim", R.drawable.cinema));
        data.getListPlace().add(new Word("factory", "nhà máy", R.drawable.factory));
        data.getListPlace().add(new Word("hotel", "khách sạn", R.drawable.hotel));
        data.getListPlace().add(new Word("library", "thư viện", R.drawable.library));
        data.getListPlace().add(new Word("market", "chợ", R.drawable.market));
        data.getListPlace().add(new Word("bridge", "cây cầu", R.drawable.bridge));
        data.getListPlace().add(new Word("beach", "bãi biển", R.drawable.beach));
        data.getListPlace().add(new Word("church", "nhà thờ", R.drawable.church));
        data.getListPlace().add(new Word("island", "hòn đảo", R.drawable.island));
        data.getListPlace().add(new Word("lake", "cái hồ", R.drawable.lake));
        data.getListPlace().add(new Word("river", "con sông", R.drawable.river));
        data.getListPlace().add(new Word("park", "công viên", R.drawable.park));
        data.getListPlace().add(new Word("pagoda", "ngôi chùa", R.drawable.pagoda));
        data.getListPlace().add(new Word("school", "trường học", R.drawable.school));
        data.getListPlace().add(new Word("supermarket", "siêu thị", R.drawable.supermarket));
        data.getListPlace().add(new Word("stadium", "sân vận động", R.drawable.stadium));
        data.getListPlace().add(new Word("restaurant", "nhà hàng", R.drawable.restaurant));
        data.getListPlace().add(new Word("mountain", "ngọn núi", R.drawable.mountain));
        data.getListPlace().add(new Word("museum", "bảo tàng", R.drawable.museum));
        data.getListPlace().add(new Word("hospital", "bệnh viện", R.drawable.hospital));
        data.getListPlace().add(new Word("bakery", "cửa hàng bánh", R.drawable.bakery));

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
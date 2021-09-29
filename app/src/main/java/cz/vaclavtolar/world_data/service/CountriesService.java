package cz.vaclavtolar.world_data.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.vaclavtolar.world_data.dto.Country;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static cz.vaclavtolar.world_data.service.CountriesApi.ACCESS_KEY;
import static cz.vaclavtolar.world_data.service.CountriesApi.BASE_URL;

public class CountriesService {

    final static Map<String, CountryNames> countryNamesByIso2 = new HashMap<>();

    static {
        countryNamesByIso2.put("AD", new CountryNames("Andorra", "安道尔", "أندورا"));
        countryNamesByIso2.put("AE", new CountryNames("Spojené arabské emiráty", "阿联酋","الإمارات العربية المتحدة"));
        countryNamesByIso2.put("AF", new CountryNames("Afghánistán", "阿富汗","أفغانستان"));
        countryNamesByIso2.put("AG", new CountryNames("Antigua a Barbuda", "安提瓜和巴布达","أنتيغوا/بربودا"));
        countryNamesByIso2.put("AI", new CountryNames("Anguilla", "安圭拉","أنجويلا"));
        countryNamesByIso2.put("AL", new CountryNames("Albánie", "阿尔巴尼亚","ألبانيا"));
        countryNamesByIso2.put("AM", new CountryNames("Arménie", "亚美尼亚","أرمينيا"));
        countryNamesByIso2.put("AO", new CountryNames("Angola", "安哥拉","أنغولا"));
        countryNamesByIso2.put("AQ", new CountryNames("Antarktida", "南极洲","أنتاركتيكا"));
        countryNamesByIso2.put("AR", new CountryNames("Argentina", "阿根廷","الأرجنتين"));
        countryNamesByIso2.put("AS", new CountryNames("Americká Samoa", "美属萨摩亚","ساموا الأمريكية"));
        countryNamesByIso2.put("AT", new CountryNames("Rakousko", "奥地利","النمسا"));
        countryNamesByIso2.put("AU", new CountryNames("Austrálie", "澳大利亚","أستراليا"));
        countryNamesByIso2.put("AW", new CountryNames("Aruba", "阿鲁巴","أروبا"));
        countryNamesByIso2.put("AX", new CountryNames("Alandy", "奥兰群岛","جزر أولند"));
        countryNamesByIso2.put("AZ", new CountryNames("Ázerbájdžán", "阿塞拜疆","أذربيجان"));
        countryNamesByIso2.put("BA", new CountryNames("Bosna a Hercegovina", "波黑","البوسنة/الهرسك"));
        countryNamesByIso2.put("BB", new CountryNames("Barbados", "巴巴多斯","بربادوس"));
        countryNamesByIso2.put("BD", new CountryNames("Bangladéš", "孟加拉国","بنغلاديش"));
        countryNamesByIso2.put("BE", new CountryNames("Belgie", "比利时","بلجيكا"));
        countryNamesByIso2.put("BF", new CountryNames("Burkina Faso", "布基纳法索","بوركينا فاسو"));
        countryNamesByIso2.put("BG", new CountryNames("Bulharsko", "保加利亚","بلغاريا"));
        countryNamesByIso2.put("BH", new CountryNames("Bahrajn", "巴林","البحرين"));
        countryNamesByIso2.put("BI", new CountryNames("Burundi", "布隆迪","بوروندي"));
        countryNamesByIso2.put("BJ", new CountryNames("Benin", "贝宁","بنين"));
        countryNamesByIso2.put("BL", new CountryNames("Svatý Bartoloměj", "圣巴泰勒米岛","سانت بارتليمي"));
        countryNamesByIso2.put("BM", new CountryNames("Bermudy", "百慕大","جزر برمود"));
        countryNamesByIso2.put("BN", new CountryNames("Brunej", "文莱","بروني"));
        countryNamesByIso2.put("BO", new CountryNames("Bolívie", "玻利维亚","بوليفيا"));
        countryNamesByIso2.put("BQ", new CountryNames("Bonaire, Svatý Eustach a Saba", "荷兰加勒比区","بونير وسانت أوستاش وسابا"));
        countryNamesByIso2.put("BR", new CountryNames("Brazílie", "巴西","البرازيل"));
        countryNamesByIso2.put("BS", new CountryNames("Bahamy", "巴哈马","الباهاماس"));
        countryNamesByIso2.put("BT", new CountryNames("Bhútán", "不丹","بوتان"));
        countryNamesByIso2.put("BV", new CountryNames("Bouvetův ostrov", "布韦岛","جزيرة بوفيه"));
        countryNamesByIso2.put("BW", new CountryNames("Botswana", "博茨瓦纳","بوتسوانا"));
        countryNamesByIso2.put("BY", new CountryNames("Bělorusko", "白俄罗斯","روسيا البيضاء"));
        countryNamesByIso2.put("BZ", new CountryNames("Belize", "伯利兹","بيليز"));
        countryNamesByIso2.put("CA", new CountryNames("Kanada", "加拿大","كندا"));
        countryNamesByIso2.put("CC", new CountryNames("Kokosové (Keelingovy) ostrovy", "科科斯群岛","جزر كوكس(كيلينغ)"));
        countryNamesByIso2.put("CD", new CountryNames("Konžská demokratická republika", "刚果（金）","جمهورية الكونغو الديمقراطية"));
        countryNamesByIso2.put("CF", new CountryNames("Středoafrická republika", "中非","جمهورية أفريقيا الوسطى"));
        countryNamesByIso2.put("CG", new CountryNames("Konžská republika", "刚果（布）","الكونغو"));
        countryNamesByIso2.put("CH", new CountryNames("Švýcarsko", "瑞士","سويسرا"));
        countryNamesByIso2.put("CI", new CountryNames("Pobřeží slonoviny", "科特迪瓦","ساحل العاج"));
        countryNamesByIso2.put("CK", new CountryNames("Cookovy ostrovy", "库克群岛","جزر كوك"));
        countryNamesByIso2.put("CL", new CountryNames("Chile", "智利","شيلي"));
        countryNamesByIso2.put("CM", new CountryNames("Kamerun", "喀麦隆","كاميرون"));
        countryNamesByIso2.put("CN", new CountryNames("Čína", "中国","الصين"));
        countryNamesByIso2.put("CO", new CountryNames("Kolumbie", "哥伦比亚","كولومبيا"));
        countryNamesByIso2.put("CR", new CountryNames("Kostarika", "哥斯达黎加","كوستاريكا"));
        countryNamesByIso2.put("CU", new CountryNames("Kuba", "古巴","كوبا"));
        countryNamesByIso2.put("CV", new CountryNames("Kapverdy", "佛得角","الرأس الأخضر"));
        countryNamesByIso2.put("CW", new CountryNames("Curaçao", "库拉索","كوراكاو"));
        countryNamesByIso2.put("CX", new CountryNames("Vánoční ostrov", "圣诞岛","جزيرة كريسماس"));
        countryNamesByIso2.put("CY", new CountryNames("Kypr", "塞浦路斯","قبرص"));
        countryNamesByIso2.put("CZ", new CountryNames("Česko", "捷克","الجمهورية التشيكية"));
        countryNamesByIso2.put("DE", new CountryNames("Německo", "德国","ألمانيا"));
        countryNamesByIso2.put("DJ", new CountryNames("Džibutsko", "吉布提","جيبوتي"));
        countryNamesByIso2.put("DK", new CountryNames("Dánsko", "丹麦","الدانمارك"));
        countryNamesByIso2.put("DM", new CountryNames("Dominika", "多米尼克","دومينيكا"));
        countryNamesByIso2.put("DO", new CountryNames("Dominikánská republika", "多米尼加","الجمهورية الدومينيكية"));
        countryNamesByIso2.put("DZ", new CountryNames("Alžírsko", "阿尔及利亚","الجزائر"));
        countryNamesByIso2.put("EC", new CountryNames("Ekvádor", "厄瓜多尔","إكوادور"));
        countryNamesByIso2.put("EE", new CountryNames("Estonsko", "爱沙尼亚","استونيا"));
        countryNamesByIso2.put("EG", new CountryNames("Egypt", "埃及","مصر"));
        countryNamesByIso2.put("EH", new CountryNames("Západní Sahara", "西撒哈拉","الصحراء الغربية"));
        countryNamesByIso2.put("ER", new CountryNames("Eritrea", "厄立特里亚","إريتريا"));
        countryNamesByIso2.put("ES", new CountryNames("Španělsko", "西班牙","إسبانيا"));
        countryNamesByIso2.put("ET", new CountryNames("Etiopie", "埃塞俄比亚","أثيوبيا"));
        countryNamesByIso2.put("FI", new CountryNames("Finsko", "芬兰","فنلندا"));
        countryNamesByIso2.put("FJ", new CountryNames("Fidži", "斐济群岛","فيجي"));
        countryNamesByIso2.put("FK", new CountryNames("Falklandy (Malvíny)", "马尔维纳斯群岛（福克兰）","جزر فوكلاند(المالديف)"));
        countryNamesByIso2.put("FM", new CountryNames("Mikronésie", "密克罗尼西亚联邦","ميكرونيسيا"));
        countryNamesByIso2.put("FO", new CountryNames("Faerské ostrovy", "法罗群岛","جزر فارو"));
        countryNamesByIso2.put("FR", new CountryNames("Francie", "法国","فرنسا"));
        countryNamesByIso2.put("GA", new CountryNames("Gabon", "加蓬","الغابون"));
        countryNamesByIso2.put("GB", new CountryNames("Velká Británie", "英国","المملكة المتحدة"));
        countryNamesByIso2.put("GD", new CountryNames("Grenada", "格林纳达","غرينادا"));
        countryNamesByIso2.put("GE", new CountryNames("Gruzie", "格鲁吉亚","جيورجيا"));
        countryNamesByIso2.put("GF", new CountryNames("Francouzská Guyana", "法属圭亚那","غويانا الفرنسية"));
        countryNamesByIso2.put("GG", new CountryNames("Guernsey", "根西岛","غيرنزي"));
        countryNamesByIso2.put("GH", new CountryNames("Ghana", "加纳","غانا"));
        countryNamesByIso2.put("GI", new CountryNames("Gibraltar", "直布罗陀","جبل طارق"));
        countryNamesByIso2.put("GL", new CountryNames("Grónsko", "格陵兰","جرينلاند"));
        countryNamesByIso2.put("GM", new CountryNames("Gambie", "冈比亚","غامبيا"));
        countryNamesByIso2.put("GN", new CountryNames("Guinea", "几内亚","غينيا"));
        countryNamesByIso2.put("GP", new CountryNames("Guadeloupe", "瓜德罗普"," جزر جوادلوب"));
        countryNamesByIso2.put("GQ", new CountryNames("Rovníková Guinea", "赤道几内亚","غينيا الاستوائي"));
        countryNamesByIso2.put("GR", new CountryNames("Řecko", "希腊","اليونان"));
        countryNamesByIso2.put("GS", new CountryNames("Jižní Georgie a Jižní Sandwichovy ostrovy", "南乔治亚岛和南桑威奇群岛","جورجيا الجنوبية/جزر ساندويتش"));
        countryNamesByIso2.put("GT", new CountryNames("Guatemala", "危地马拉","غواتيمال"));
        countryNamesByIso2.put("GU", new CountryNames("Guam", "关岛","جوام"));
        countryNamesByIso2.put("GW", new CountryNames("Guinea-Bissau", "几内亚比绍","غينيا-بيساو"));
        countryNamesByIso2.put("GY", new CountryNames("Guyana", "圭亚那","غيانا"));
        countryNamesByIso2.put("HK", new CountryNames("Hongkong", "香港","هونغ كونغ"));
        countryNamesByIso2.put("HM", new CountryNames("Heardův ostrov a MacDonaldovy ostrovy", "赫德岛和麦克唐纳群岛","جزيرة هيرد/جزر ماكدونالد"));
        countryNamesByIso2.put("HN", new CountryNames("Honduras", "洪都拉斯","هندوراس"));
        countryNamesByIso2.put("HR", new CountryNames("Chorvatsko", "克罗地亚","كرواتيا"));
        countryNamesByIso2.put("HT", new CountryNames("Haiti", "海地","هايتي"));
        countryNamesByIso2.put("HU", new CountryNames("Maďarsko", "匈牙利","هنغاريا"));
        countryNamesByIso2.put("ID", new CountryNames("Indonésie", "印尼","أندونيسيا"));
        countryNamesByIso2.put("IE", new CountryNames("Irsko", "爱尔兰","أيرلندا"));
        countryNamesByIso2.put("IL", new CountryNames("Izrael", "以色列","إسرائيل"));
        countryNamesByIso2.put("IM", new CountryNames("Man", "马恩岛","جزيرة مان"));
        countryNamesByIso2.put("IN", new CountryNames("Indie", "印度","الهند"));
        countryNamesByIso2.put("IO", new CountryNames("Britské indickooceánské území", "英属印度洋领地","إقليم المحيط البريطاني الهندي"));
        countryNamesByIso2.put("IQ", new CountryNames("Irák", "伊拉克","العراق"));
        countryNamesByIso2.put("IR", new CountryNames("Írán", "伊朗","إيران"));
        countryNamesByIso2.put("IS", new CountryNames("Island", "冰岛","آيسلندا"));
        countryNamesByIso2.put("IT", new CountryNames("Itálie", "意大利","إيطاليا"));
        countryNamesByIso2.put("JE", new CountryNames("Jersey", "泽西岛","جيرزي"));
        countryNamesByIso2.put("JM", new CountryNames("Jamajka", "牙买加","جمايكا"));
        countryNamesByIso2.put("JO", new CountryNames("Jordánsko", "约旦","الأردن"));
        countryNamesByIso2.put("JP", new CountryNames("Japonsko", "日本","اليابان"));
        countryNamesByIso2.put("KE", new CountryNames("Keňa", "肯尼亚","كينيا"));
        countryNamesByIso2.put("KG", new CountryNames("Kyrgyzstán", "吉尔吉斯斯坦","قيرغيزستان"));
        countryNamesByIso2.put("KH", new CountryNames("Kambodža", "柬埔寨","كمبوديا"));
        countryNamesByIso2.put("KI", new CountryNames("Kiribati", "基里巴斯","كيريباتي"));
        countryNamesByIso2.put("KM", new CountryNames("Komory", "科摩罗","جزر القمر"));
        countryNamesByIso2.put("KN", new CountryNames("Svatý Kryštof a Nevis", "圣基茨和尼维斯","سانت كيتس/نيفيس"));
        countryNamesByIso2.put("KP", new CountryNames("Korejská lidově demokratická republika", "北朝鲜","كوريا الشمالية"));
        countryNamesByIso2.put("KR", new CountryNames("Korejská republika", "韩国","كوريا الجنوبية"));
        countryNamesByIso2.put("KW", new CountryNames("Kuvajt", "科威特","الكويت"));
        countryNamesByIso2.put("KY", new CountryNames("Kajmanské ostrovy", "开曼群岛","جزر كايمان"));
        countryNamesByIso2.put("KZ", new CountryNames("Kazachstán", "哈萨克斯坦","كازاخستان"));
        countryNamesByIso2.put("LA", new CountryNames("Laos", "老挝","لاوس"));
        countryNamesByIso2.put("LB", new CountryNames("Libanon", "黎巴嫩","لبنان"));
        countryNamesByIso2.put("LC", new CountryNames("Svatá Lucie", "圣卢西亚","سانت لوسيا"));
        countryNamesByIso2.put("LI", new CountryNames("Lichtenštejnsko", "列支敦士登","ليختنشتين"));
        countryNamesByIso2.put("LK", new CountryNames("Šrí Lanka", "斯里兰卡","سريلانكا"));
        countryNamesByIso2.put("LR", new CountryNames("Libérie", "利比里亚","ليبيريا"));
        countryNamesByIso2.put("LS", new CountryNames("Lesotho", "莱索托","ليسوتو"));
        countryNamesByIso2.put("LT", new CountryNames("Litva", "立陶宛","لتوانيا"));
        countryNamesByIso2.put("LU", new CountryNames("Lucembursko", "卢森堡","لوكسمبورغ"));
        countryNamesByIso2.put("LV", new CountryNames("Lotyšsko", "拉脱维亚","لاتفيا"));
        countryNamesByIso2.put("LY", new CountryNames("Libye", "利比亚","ليبيا"));
        countryNamesByIso2.put("MA", new CountryNames("Maroko", "摩洛哥","المغرب"));
        countryNamesByIso2.put("MC", new CountryNames("Monako", "摩纳哥","موناكو"));
        countryNamesByIso2.put("MD", new CountryNames("Moldavsko", "摩尔多瓦","مولدافيا"));
        countryNamesByIso2.put("ME", new CountryNames("Černá Hora", "黑山","الجبل الأسو"));
        countryNamesByIso2.put("MF", new CountryNames("Svatý Martin (FR)", "法属圣马丁","سانت مارتن"));
        countryNamesByIso2.put("MG", new CountryNames("Madagaskar", "马达加斯加","مدغشقر"));
        countryNamesByIso2.put("MH", new CountryNames("Marshallovy ostrovy", "马绍尔群岛","جزر مارشال"));
        countryNamesByIso2.put("MK", new CountryNames("Severní Makedonie", "马其顿","مقدونيا"));
        countryNamesByIso2.put("ML", new CountryNames("Mali", "马里","مالي"));
        countryNamesByIso2.put("MM", new CountryNames("Myanmar", "缅甸","ميانمار"));
        countryNamesByIso2.put("MN", new CountryNames("Mongolsko", "蒙古","منغوليا"));
        countryNamesByIso2.put("MO", new CountryNames("Macao", "澳门","ماكاو"));
        countryNamesByIso2.put("MP", new CountryNames("Severní Mariany", "北马里亚纳群岛","جزر ماريانا الشمالية"));
        countryNamesByIso2.put("MQ", new CountryNames("Martinik", "马提尼克","مارتينيك"));
        countryNamesByIso2.put("MR", new CountryNames("Mauritánie", "毛里塔尼亚","موريتانيا"));
        countryNamesByIso2.put("MS", new CountryNames("Montserrat", "蒙塞拉特岛","مونتسيرات"));
        countryNamesByIso2.put("MT", new CountryNames("Malta", "马耳他","مالطا"));
        countryNamesByIso2.put("MU", new CountryNames("Mauricius", "毛里求斯","موريشيوس"));
        countryNamesByIso2.put("MV", new CountryNames("Maledivy", "马尔代夫","المالديف"));
        countryNamesByIso2.put("MW", new CountryNames("Malawi", "马拉维","مالاوي"));
        countryNamesByIso2.put("MX", new CountryNames("Mexiko", "墨西哥","المكسيك"));
        countryNamesByIso2.put("MY", new CountryNames("Malajsie", "马来西亚","ماليزيا"));
        countryNamesByIso2.put("MZ", new CountryNames("Mosambik", "莫桑比克","موزمبيق"));
        countryNamesByIso2.put("NA", new CountryNames("Namibie", "纳米比亚","ناميبيا"));
        countryNamesByIso2.put("NC", new CountryNames("Nová Kaledonie", "新喀里多尼亚","كاليدونيا الجديدة"));
        countryNamesByIso2.put("NE", new CountryNames("Niger", "尼日尔","النيجر"));
        countryNamesByIso2.put("NF", new CountryNames("Norfolk", "诺福克岛","جزيرة نورفولك"));
        countryNamesByIso2.put("NG", new CountryNames("Nigérie", "尼日利亚","نيجيرياs"));
        countryNamesByIso2.put("NI", new CountryNames("Nikaragua", "尼加拉瓜","نيكاراجوا"));
        countryNamesByIso2.put("NL", new CountryNames("Nizozemsko", "荷兰","هولندا"));
        countryNamesByIso2.put("NO", new CountryNames("Norsko", "挪威","النرويج"));
        countryNamesByIso2.put("NP", new CountryNames("Nepál", "尼泊尔","نيبال"));
        countryNamesByIso2.put("NR", new CountryNames("Nauru", "瑙鲁","ناورو"));
        countryNamesByIso2.put("NU", new CountryNames("Niue", "纽埃","نييوي"));
        countryNamesByIso2.put("NZ", new CountryNames("Nový Zéland", "新西兰","نيوزيلندا"));
        countryNamesByIso2.put("OM", new CountryNames("Omán", "阿曼","عُمان"));
        countryNamesByIso2.put("PA", new CountryNames("Panama", "巴拿马","بنما"));
        countryNamesByIso2.put("PE", new CountryNames("Peru", "秘鲁","بيرو"));
        countryNamesByIso2.put("PF", new CountryNames("Francouzská Polynésie", "法属波利尼西亚","بولينيزيا الفرنسية"));
        countryNamesByIso2.put("PG", new CountryNames("Papua Nová Guinea", "巴布亚新几内亚","بابوا غينيا الجديدة"));
        countryNamesByIso2.put("PH", new CountryNames("Filipíny", "菲律宾","الفليبين"));
        countryNamesByIso2.put("PK", new CountryNames("Pákistán", "巴基斯坦","باكستان"));
        countryNamesByIso2.put("PL", new CountryNames("Polsko", "波兰","بولندا"));
        countryNamesByIso2.put("PM", new CountryNames("Saint Pierre a Miquelon", "圣皮埃尔和密克隆","سانت بيير/ميكلون"));
        countryNamesByIso2.put("PN", new CountryNames("Pitcairn", "皮特凯恩群岛","بيتكيرن"));
        countryNamesByIso2.put("PR", new CountryNames("Portoriko", "波多黎各","بورتوريكو"));
        countryNamesByIso2.put("PS", new CountryNames("Palestina", "巴勒斯坦","فلسطين"));
        countryNamesByIso2.put("PT", new CountryNames("Portugalsko", "葡萄牙","البرتغال"));
        countryNamesByIso2.put("PW", new CountryNames("Palau", "帕劳","بالاو"));
        countryNamesByIso2.put("PY", new CountryNames("Paraguay", "巴拉圭","باراغواي"));
        countryNamesByIso2.put("QA", new CountryNames("Katar", "卡塔尔","قطر"));
        countryNamesByIso2.put("RE", new CountryNames("Réunion", "留尼汪","ريونيون"));
        countryNamesByIso2.put("RO", new CountryNames("Rumunsko", "罗马尼亚","رومانيا"));
        countryNamesByIso2.put("RS", new CountryNames("Srbsko", "塞尔维亚","صربيا"));
        countryNamesByIso2.put("RU", new CountryNames("Rusko", "俄罗斯","روسيا"));
        countryNamesByIso2.put("RW", new CountryNames("Rwanda", "卢旺达","رواندا"));
        countryNamesByIso2.put("SA", new CountryNames("Saúdská Arábie", "沙特阿拉伯","المملكة العربية السعودية"));
        countryNamesByIso2.put("SB", new CountryNames("Šalomounovy ostrovy", "所罗门群岛","جزر سليمان"));
        countryNamesByIso2.put("SC", new CountryNames("Seychely", "塞舌尔","سيشيل"));
        countryNamesByIso2.put("SD", new CountryNames("Súdán", "苏丹","السودان"));
        countryNamesByIso2.put("SE", new CountryNames("Švédsko", "瑞典","السويد"));
        countryNamesByIso2.put("SG", new CountryNames("Singapur", "新加坡","سنغافورة"));
        countryNamesByIso2.put("SH", new CountryNames("Svatá Helena", "圣赫勒拿","سانت هيلانة"));
        countryNamesByIso2.put("SI", new CountryNames("Slovinsko", "斯洛文尼亚","سلوفينيا"));
        countryNamesByIso2.put("SJ", new CountryNames("Špicberky a Jan Mayen", "斯瓦尔巴群岛和扬马延岛","سفالبارد/يان ماين"));
        countryNamesByIso2.put("SK", new CountryNames("Slovensko", "斯洛伐克","سلوفاكيا"));
        countryNamesByIso2.put("SL", new CountryNames("Sierra Leone", "塞拉利昂","سيراليون"));
        countryNamesByIso2.put("SM", new CountryNames("San Marino", "圣马力诺","سان مارينو"));
        countryNamesByIso2.put("SN", new CountryNames("Senegal", "塞内加尔","السنغال"));
        countryNamesByIso2.put("SO", new CountryNames("Somálsko", "索马里","الصومال"));
        countryNamesByIso2.put("SR", new CountryNames("Surinam", "苏里南","سورينام"));
        countryNamesByIso2.put("SS", new CountryNames("Jižní Súdán", "南苏丹","جنوب السودان"));
        countryNamesByIso2.put("ST", new CountryNames("Svatý Tomáš a Princův ostrov", "圣多美和普林西比","ساو تومي/برينسيب"));
        countryNamesByIso2.put("SV", new CountryNames("Salvador", "萨尔瓦多","إلسلفادور"));
        countryNamesByIso2.put("SX", new CountryNames("Svatý Martin (NL)", "荷属圣马丁","سينت مارتن"));
        countryNamesByIso2.put("SY", new CountryNames("Sýrie", "叙利亚","سورية"));
        countryNamesByIso2.put("SZ", new CountryNames("Svazijsko", "斯威士兰","سوازيلند"));
        countryNamesByIso2.put("TC", new CountryNames("Turks a Caicos", "特克斯和凯科斯群岛","جزر توركس/كايكوس"));
        countryNamesByIso2.put("TD", new CountryNames("Čad", "乍得","تشاد"));
        countryNamesByIso2.put("TF", new CountryNames("Francouzská jižní a antarktická území", "法属南部领地","أراض فرنسية جنوبية"));
        countryNamesByIso2.put("TG", new CountryNames("Togo", "多哥","توغو"));
        countryNamesByIso2.put("TH", new CountryNames("Thajsko", "泰国","تايلندا"));
        countryNamesByIso2.put("TJ", new CountryNames("Tádžikistán", "塔吉克斯坦","طاجيكستان"));
        countryNamesByIso2.put("TK", new CountryNames("Tokelau", "托克劳","توكلو"));
        countryNamesByIso2.put("TL", new CountryNames("Východní Timor", "东帝汶","تيمور الشرقية"));
        countryNamesByIso2.put("TM", new CountryNames("Turkmenistán", "土库曼斯坦","تركمانستان"));
        countryNamesByIso2.put("TN", new CountryNames("Tunisko", "突尼斯","تونس"));
        countryNamesByIso2.put("TO", new CountryNames("Tonga", "汤加","تونغا"));
        countryNamesByIso2.put("TR", new CountryNames("Turecko", "土耳其","تركيا"));
        countryNamesByIso2.put("TT", new CountryNames("Trinidad a Tobago", "特立尼达和多巴哥","ترينيداد وتوباغو"));
        countryNamesByIso2.put("TV", new CountryNames("Tuvalu", "图瓦卢","توفالو"));
        countryNamesByIso2.put("TW", new CountryNames("Tchaj-wan", "台湾","تايوان"));
        countryNamesByIso2.put("TZ", new CountryNames("Tanzanie", "坦桑尼亚","تنزانيا"));
        countryNamesByIso2.put("UA", new CountryNames("Ukrajina", "乌克兰","أوكرانيا"));
        countryNamesByIso2.put("UG", new CountryNames("Uganda", "乌干达","أوغندا"));
        countryNamesByIso2.put("UM", new CountryNames("Menší odlehlé ostrovy USA", "美国本土外小岛屿","جزر الولايات المتحدة الصغيرة"));
        countryNamesByIso2.put("US", new CountryNames("Spojené státy", "美国","الولايات المتحدة الأمريكية"));
        countryNamesByIso2.put("UY", new CountryNames("Uruguay", "乌拉圭","أورغواي"));
        countryNamesByIso2.put("UZ", new CountryNames("Uzbekistán", "乌兹别克斯坦","أوزباكستان"));
        countryNamesByIso2.put("VA", new CountryNames("Vatikán", "梵蒂冈","دولة مدينة الفاتيكان"));
        countryNamesByIso2.put("VC", new CountryNames("Svatý Vincenc a Grenadiny", "圣文森特和格林纳丁斯","سانت فنسنت/الجرينادين"));
        countryNamesByIso2.put("VE", new CountryNames("Venezuela", "委内瑞拉","فنزويلا"));
        countryNamesByIso2.put("VG", new CountryNames("Britské Panenské ostrovy", "英属维尔京群岛","الجزر العذراء البريطانية"));
        countryNamesByIso2.put("VI", new CountryNames("Americké Panenské ostrovy", "美属维尔京群岛","جزر فيرجن الأمريكية"));
        countryNamesByIso2.put("VN", new CountryNames("Vietnam", "越南","فيتنام"));
        countryNamesByIso2.put("VU", new CountryNames("Vanuatu", "瓦努阿图","فانواتو"));
        countryNamesByIso2.put("WF", new CountryNames("Wallis a Futuna", "瓦利斯和富图纳","والس/فوتونا"));
        countryNamesByIso2.put("WS", new CountryNames("Samoa", "萨摩亚","ساموا"));
        countryNamesByIso2.put("XK", new CountryNames("Kosovo", "科索沃","كوسوفو"));
        countryNamesByIso2.put("YE", new CountryNames("Jemen", "也门","اليمن"));
        countryNamesByIso2.put("YT", new CountryNames("Mayotte", "马约特","مايوت"));
        countryNamesByIso2.put("ZA", new CountryNames("Jižní Afrika", "南非","جنوب أفريقيا"));
        countryNamesByIso2.put("ZM", new CountryNames("Zambie", "赞比亚","زامبيا"));
        countryNamesByIso2.put("ZW", new CountryNames("Zimbabwe", "津巴布韦","زمبابوي"));
    }


    private static CountriesService instance;

    private CountriesApi countriesApi;

    private CountriesService() {
        initRestApiClient();
    }

    public static CountriesService getInstance() {
        if (instance == null) {
            instance = new CountriesService();
        }
        return instance;
    }

    private void initRestApiClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .client(httpClient)
                .build();
        countriesApi = retrofit.create(CountriesApi.class);
    }

    public Call<List<Country>> getAllCountries() {
        return countriesApi.getAllCountries(ACCESS_KEY);
    }

    public String getCountryCzechName(String iso2) {
        return countryNamesByIso2.get(iso2).getCzechName();
    }

    public String getCountryChineseName(String iso2) {
        return countryNamesByIso2.get(iso2).getChineseName();
    }

    public String getCountryArabicName(String iso2) {
        return countryNamesByIso2.get(iso2).getArabicName();
    }

    public static class CountryNames {
        private String czechName;
        private String chineseName;
        private String arabicName;

        public CountryNames(String czechName, String chineseName, String arabicName) {
            this.czechName = czechName;
            this.chineseName = chineseName;
            this.arabicName = arabicName;
        }

        public String getCzechName() {
            return czechName;
        }

        public String getChineseName() {
            return chineseName;
        }

        public String getArabicName() {
            return arabicName;
        }
    }


}

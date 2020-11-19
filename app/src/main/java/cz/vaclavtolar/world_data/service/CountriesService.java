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
        countryNamesByIso2.put("DE", new CountryNames("Německo", "德国",""));
        countryNamesByIso2.put("DJ", new CountryNames("Džibutsko", "吉布提",""));
        countryNamesByIso2.put("DK", new CountryNames("Dánsko", "丹麦",""));
        countryNamesByIso2.put("DM", new CountryNames("Dominika", "多米尼克",""));
        countryNamesByIso2.put("DO", new CountryNames("Dominikánská republika", "多米尼加",""));
        countryNamesByIso2.put("DZ", new CountryNames("Alžírsko", "阿尔及利亚",""));
        countryNamesByIso2.put("EC", new CountryNames("Ekvádor", "厄瓜多尔",""));
        countryNamesByIso2.put("EE", new CountryNames("Estonsko", "爱沙尼亚",""));
        countryNamesByIso2.put("EG", new CountryNames("Egypt", "埃及",""));
        countryNamesByIso2.put("EH", new CountryNames("Západní Sahara", "西撒哈拉",""));
        countryNamesByIso2.put("ER", new CountryNames("Eritrea", "厄立特里亚",""));
        countryNamesByIso2.put("ES", new CountryNames("Španělsko", "西班牙",""));
        countryNamesByIso2.put("ET", new CountryNames("Etiopie", "埃塞俄比亚",""));
        countryNamesByIso2.put("FI", new CountryNames("Finsko", "芬兰",""));
        countryNamesByIso2.put("FJ", new CountryNames("Fidži", "斐济群岛",""));
        countryNamesByIso2.put("FK", new CountryNames("Falklandy (Malvíny)", "马尔维纳斯群岛（福克兰）",""));
        countryNamesByIso2.put("FM", new CountryNames("Mikronésie", "密克罗尼西亚联邦",""));
        countryNamesByIso2.put("FO", new CountryNames("Faerské ostrovy", "法罗群岛",""));
        countryNamesByIso2.put("FR", new CountryNames("Francie", "法国",""));
        countryNamesByIso2.put("GA", new CountryNames("Gabon", "加蓬",""));
        countryNamesByIso2.put("GB", new CountryNames("Velká Británie", "英国",""));
        countryNamesByIso2.put("GD", new CountryNames("Grenada", "格林纳达",""));
        countryNamesByIso2.put("GE", new CountryNames("Gruzie", "格鲁吉亚",""));
        countryNamesByIso2.put("GF", new CountryNames("Francouzská Guyana", "法属圭亚那",""));
        countryNamesByIso2.put("GG", new CountryNames("Guernsey", "根西岛",""));
        countryNamesByIso2.put("GH", new CountryNames("Ghana", "加纳",""));
        countryNamesByIso2.put("GI", new CountryNames("Gibraltar", "直布罗陀",""));
        countryNamesByIso2.put("GL", new CountryNames("Grónsko", "格陵兰",""));
        countryNamesByIso2.put("GM", new CountryNames("Gambie", "冈比亚",""));
        countryNamesByIso2.put("GN", new CountryNames("Guinea", "几内亚",""));
        countryNamesByIso2.put("GP", new CountryNames("Guadeloupe", "瓜德罗普",""));
        countryNamesByIso2.put("GQ", new CountryNames("Rovníková Guinea", "赤道几内亚",""));
        countryNamesByIso2.put("GR", new CountryNames("Řecko", "希腊",""));
        countryNamesByIso2.put("GS", new CountryNames("Jižní Georgie a Jižní Sandwichovy ostrovy", "南乔治亚岛和南桑威奇群岛",""));
        countryNamesByIso2.put("GT", new CountryNames("Guatemala", "危地马拉",""));
        countryNamesByIso2.put("GU", new CountryNames("Guam", "关岛",""));
        countryNamesByIso2.put("GW", new CountryNames("Guinea-Bissau", "几内亚比绍",""));
        countryNamesByIso2.put("GY", new CountryNames("Guyana", "圭亚那",""));
        countryNamesByIso2.put("HK", new CountryNames("Hongkong", "香港",""));
        countryNamesByIso2.put("HM", new CountryNames("Heardův ostrov a MacDonaldovy ostrovy", "赫德岛和麦克唐纳群岛",""));
        countryNamesByIso2.put("HN", new CountryNames("Honduras", "洪都拉斯",""));
        countryNamesByIso2.put("HR", new CountryNames("Chorvatsko", "克罗地亚",""));
        countryNamesByIso2.put("HT", new CountryNames("Haiti", "海地",""));
        countryNamesByIso2.put("HU", new CountryNames("Maďarsko", "匈牙利",""));
        countryNamesByIso2.put("ID", new CountryNames("Indonésie", "印尼",""));
        countryNamesByIso2.put("IE", new CountryNames("Irsko", "爱尔兰",""));
        countryNamesByIso2.put("IL", new CountryNames("Izrael", "以色列",""));
        countryNamesByIso2.put("IM", new CountryNames("Man", "马恩岛",""));
        countryNamesByIso2.put("IN", new CountryNames("Indie", "印度",""));
        countryNamesByIso2.put("IO", new CountryNames("Britské indickooceánské území", "英属印度洋领地",""));
        countryNamesByIso2.put("IQ", new CountryNames("Irák", "伊拉克",""));
        countryNamesByIso2.put("IR", new CountryNames("Írán", "伊朗",""));
        countryNamesByIso2.put("IS", new CountryNames("Island", "冰岛",""));
        countryNamesByIso2.put("IT", new CountryNames("Itálie", "意大利",""));
        countryNamesByIso2.put("JE", new CountryNames("Jersey", "泽西岛",""));
        countryNamesByIso2.put("JM", new CountryNames("Jamajka", "牙买加",""));
        countryNamesByIso2.put("JO", new CountryNames("Jordánsko", "约旦",""));
        countryNamesByIso2.put("JP", new CountryNames("Japonsko", "日本",""));
        countryNamesByIso2.put("KE", new CountryNames("Keňa", "肯尼亚",""));
        countryNamesByIso2.put("KG", new CountryNames("Kyrgyzstán", "吉尔吉斯斯坦",""));
        countryNamesByIso2.put("KH", new CountryNames("Kambodža", "柬埔寨",""));
        countryNamesByIso2.put("KI", new CountryNames("Kiribati", "基里巴斯",""));
        countryNamesByIso2.put("KM", new CountryNames("Komory", "科摩罗",""));
        countryNamesByIso2.put("KN", new CountryNames("Svatý Kryštof a Nevis", "圣基茨和尼维斯",""));
        countryNamesByIso2.put("KP", new CountryNames("Korejská lidově demokratická republika", "北朝鲜",""));
        countryNamesByIso2.put("KR", new CountryNames("Korejská republika", "韩国",""));
        countryNamesByIso2.put("KW", new CountryNames("Kuvajt", "科威特",""));
        countryNamesByIso2.put("KY", new CountryNames("Kajmanské ostrovy", "开曼群岛",""));
        countryNamesByIso2.put("KZ", new CountryNames("Kazachstán", "哈萨克斯坦",""));
        countryNamesByIso2.put("LA", new CountryNames("Laos", "老挝",""));
        countryNamesByIso2.put("LB", new CountryNames("Libanon", "黎巴嫩",""));
        countryNamesByIso2.put("LC", new CountryNames("Svatá Lucie", "圣卢西亚",""));
        countryNamesByIso2.put("LI", new CountryNames("Lichtenštejnsko", "列支敦士登",""));
        countryNamesByIso2.put("LK", new CountryNames("Šrí Lanka", "斯里兰卡",""));
        countryNamesByIso2.put("LR", new CountryNames("Libérie", "利比里亚",""));
        countryNamesByIso2.put("LS", new CountryNames("Lesotho", "莱索托",""));
        countryNamesByIso2.put("LT", new CountryNames("Litva", "立陶宛",""));
        countryNamesByIso2.put("LU", new CountryNames("Lucembursko", "卢森堡",""));
        countryNamesByIso2.put("LV", new CountryNames("Lotyšsko", "拉脱维亚",""));
        countryNamesByIso2.put("LY", new CountryNames("Libye", "利比亚",""));
        countryNamesByIso2.put("MA", new CountryNames("Maroko", "摩洛哥",""));
        countryNamesByIso2.put("MC", new CountryNames("Monako", "摩纳哥",""));
        countryNamesByIso2.put("MD", new CountryNames("Moldavsko", "摩尔多瓦",""));
        countryNamesByIso2.put("ME", new CountryNames("Černá Hora", "黑山",""));
        countryNamesByIso2.put("MF", new CountryNames("Svatý Martin (FR)", "法属圣马丁",""));
        countryNamesByIso2.put("MG", new CountryNames("Madagaskar", "马达加斯加",""));
        countryNamesByIso2.put("MH", new CountryNames("Marshallovy ostrovy", "马绍尔群岛",""));
        countryNamesByIso2.put("MK", new CountryNames("Severní Makedonie", "马其顿",""));
        countryNamesByIso2.put("ML", new CountryNames("Mali", "马里",""));
        countryNamesByIso2.put("MM", new CountryNames("Myanmar", "缅甸",""));
        countryNamesByIso2.put("MN", new CountryNames("Mongolsko", "蒙古",""));
        countryNamesByIso2.put("MO", new CountryNames("Macao", "澳门",""));
        countryNamesByIso2.put("MP", new CountryNames("Severní Mariany", "北马里亚纳群岛",""));
        countryNamesByIso2.put("MQ", new CountryNames("Martinik", "马提尼克",""));
        countryNamesByIso2.put("MR", new CountryNames("Mauritánie", "毛里塔尼亚",""));
        countryNamesByIso2.put("MS", new CountryNames("Montserrat", "蒙塞拉特岛",""));
        countryNamesByIso2.put("MT", new CountryNames("Malta", "马耳他",""));
        countryNamesByIso2.put("MU", new CountryNames("Mauricius", "毛里求斯",""));
        countryNamesByIso2.put("MV", new CountryNames("Maledivy", "马尔代夫",""));
        countryNamesByIso2.put("MW", new CountryNames("Malawi", "马拉维",""));
        countryNamesByIso2.put("MX", new CountryNames("Mexiko", "墨西哥",""));
        countryNamesByIso2.put("MY", new CountryNames("Malajsie", "马来西亚",""));
        countryNamesByIso2.put("MZ", new CountryNames("Mosambik", "莫桑比克",""));
        countryNamesByIso2.put("NA", new CountryNames("Namibie", "纳米比亚",""));
        countryNamesByIso2.put("NC", new CountryNames("Nová Kaledonie", "新喀里多尼亚",""));
        countryNamesByIso2.put("NE", new CountryNames("Niger", "尼日尔",""));
        countryNamesByIso2.put("NF", new CountryNames("Norfolk", "诺福克岛",""));
        countryNamesByIso2.put("NG", new CountryNames("Nigérie", "尼日利亚",""));
        countryNamesByIso2.put("NI", new CountryNames("Nikaragua", "尼加拉瓜",""));
        countryNamesByIso2.put("NL", new CountryNames("Nizozemsko", "荷兰",""));
        countryNamesByIso2.put("NO", new CountryNames("Norsko", "挪威",""));
        countryNamesByIso2.put("NP", new CountryNames("Nepál", "尼泊尔",""));
        countryNamesByIso2.put("NR", new CountryNames("Nauru", "瑙鲁",""));
        countryNamesByIso2.put("NU", new CountryNames("Niue", "纽埃",""));
        countryNamesByIso2.put("NZ", new CountryNames("Nový Zéland", "新西兰",""));
        countryNamesByIso2.put("OM", new CountryNames("Omán", "阿曼",""));
        countryNamesByIso2.put("PA", new CountryNames("Panama", "巴拿马",""));
        countryNamesByIso2.put("PE", new CountryNames("Peru", "秘鲁",""));
        countryNamesByIso2.put("PF", new CountryNames("Francouzská Polynésie", "法属波利尼西亚",""));
        countryNamesByIso2.put("PG", new CountryNames("Papua Nová Guinea", "巴布亚新几内亚",""));
        countryNamesByIso2.put("PH", new CountryNames("Filipíny", "菲律宾",""));
        countryNamesByIso2.put("PK", new CountryNames("Pákistán", "巴基斯坦",""));
        countryNamesByIso2.put("PL", new CountryNames("Polsko", "波兰",""));
        countryNamesByIso2.put("PM", new CountryNames("Saint Pierre a Miquelon", "圣皮埃尔和密克隆",""));
        countryNamesByIso2.put("PN", new CountryNames("Pitcairn", "皮特凯恩群岛",""));
        countryNamesByIso2.put("PR", new CountryNames("Portoriko", "波多黎各",""));
        countryNamesByIso2.put("PS", new CountryNames("Palestina", "巴勒斯坦",""));
        countryNamesByIso2.put("PT", new CountryNames("Portugalsko", "葡萄牙",""));
        countryNamesByIso2.put("PW", new CountryNames("Palau", "帕劳",""));
        countryNamesByIso2.put("PY", new CountryNames("Paraguay", "巴拉圭",""));
        countryNamesByIso2.put("QA", new CountryNames("Katar", "卡塔尔",""));
        countryNamesByIso2.put("RE", new CountryNames("Réunion", "留尼汪",""));
        countryNamesByIso2.put("RO", new CountryNames("Rumunsko", "罗马尼亚",""));
        countryNamesByIso2.put("RS", new CountryNames("Srbsko", "塞尔维亚",""));
        countryNamesByIso2.put("RU", new CountryNames("Rusko", "俄罗斯",""));
        countryNamesByIso2.put("RW", new CountryNames("Rwanda", "卢旺达",""));
        countryNamesByIso2.put("SA", new CountryNames("Saúdská Arábie", "沙特阿拉伯",""));
        countryNamesByIso2.put("SB", new CountryNames("Šalomounovy ostrovy", "所罗门群岛",""));
        countryNamesByIso2.put("SC", new CountryNames("Seychely", "塞舌尔",""));
        countryNamesByIso2.put("SD", new CountryNames("Súdán", "苏丹",""));
        countryNamesByIso2.put("SE", new CountryNames("Švédsko", "瑞典",""));
        countryNamesByIso2.put("SG", new CountryNames("Singapur", "新加坡",""));
        countryNamesByIso2.put("SH", new CountryNames("Svatá Helena", "圣赫勒拿",""));
        countryNamesByIso2.put("SI", new CountryNames("Slovinsko", "斯洛文尼亚",""));
        countryNamesByIso2.put("SJ", new CountryNames("Špicberky a Jan Mayen", "斯瓦尔巴群岛和扬马延岛",""));
        countryNamesByIso2.put("SK", new CountryNames("Slovensko", "斯洛伐克",""));
        countryNamesByIso2.put("SL", new CountryNames("Sierra Leone", "塞拉利昂",""));
        countryNamesByIso2.put("SM", new CountryNames("San Marino", "圣马力诺",""));
        countryNamesByIso2.put("SN", new CountryNames("Senegal", "塞内加尔",""));
        countryNamesByIso2.put("SO", new CountryNames("Somálsko", "索马里",""));
        countryNamesByIso2.put("SR", new CountryNames("Surinam", "苏里南",""));
        countryNamesByIso2.put("SS", new CountryNames("Jižní Súdán", "南苏丹",""));
        countryNamesByIso2.put("ST", new CountryNames("Svatý Tomáš a Princův ostrov", "圣多美和普林西比",""));
        countryNamesByIso2.put("SV", new CountryNames("Salvador", "萨尔瓦多",""));
        countryNamesByIso2.put("SX", new CountryNames("Svatý Martin (NL)", "荷属圣马丁",""));
        countryNamesByIso2.put("SY", new CountryNames("Sýrie", "叙利亚",""));
        countryNamesByIso2.put("SZ", new CountryNames("Svazijsko", "斯威士兰",""));
        countryNamesByIso2.put("TC", new CountryNames("Turks a Caicos", "特克斯和凯科斯群岛",""));
        countryNamesByIso2.put("TD", new CountryNames("Čad", "乍得",""));
        countryNamesByIso2.put("TF", new CountryNames("Francouzská jižní a antarktická území", "法属南部领地",""));
        countryNamesByIso2.put("TG", new CountryNames("Togo", "多哥",""));
        countryNamesByIso2.put("TH", new CountryNames("Thajsko", "泰国",""));
        countryNamesByIso2.put("TJ", new CountryNames("Tádžikistán", "塔吉克斯坦",""));
        countryNamesByIso2.put("TK", new CountryNames("Tokelau", "托克劳",""));
        countryNamesByIso2.put("TL", new CountryNames("Východní Timor", "东帝汶",""));
        countryNamesByIso2.put("TM", new CountryNames("Turkmenistán", "土库曼斯坦",""));
        countryNamesByIso2.put("TN", new CountryNames("Tunisko", "突尼斯",""));
        countryNamesByIso2.put("TO", new CountryNames("Tonga", "汤加",""));
        countryNamesByIso2.put("TR", new CountryNames("Turecko", "土耳其",""));
        countryNamesByIso2.put("TT", new CountryNames("Trinidad a Tobago", "特立尼达和多巴哥",""));
        countryNamesByIso2.put("TV", new CountryNames("Tuvalu", "图瓦卢",""));
        countryNamesByIso2.put("TW", new CountryNames("Tchaj-wan", "台湾",""));
        countryNamesByIso2.put("TZ", new CountryNames("Tanzanie", "坦桑尼亚",""));
        countryNamesByIso2.put("UA", new CountryNames("Ukrajina", "乌克兰",""));
        countryNamesByIso2.put("UG", new CountryNames("Uganda", "乌干达",""));
        countryNamesByIso2.put("UM", new CountryNames("Menší odlehlé ostrovy USA", "美国本土外小岛屿",""));
        countryNamesByIso2.put("US", new CountryNames("Spojené státy", "美国",""));
        countryNamesByIso2.put("UY", new CountryNames("Uruguay", "乌拉圭",""));
        countryNamesByIso2.put("UZ", new CountryNames("Uzbekistán", "乌兹别克斯坦",""));
        countryNamesByIso2.put("VA", new CountryNames("Vatikán", "梵蒂冈",""));
        countryNamesByIso2.put("VC", new CountryNames("Svatý Vincenc a Grenadiny", "圣文森特和格林纳丁斯",""));
        countryNamesByIso2.put("VE", new CountryNames("Venezuela", "委内瑞拉",""));
        countryNamesByIso2.put("VG", new CountryNames("Britské Panenské ostrovy", "英属维尔京群岛",""));
        countryNamesByIso2.put("VI", new CountryNames("Americké Panenské ostrovy", "美属维尔京群岛",""));
        countryNamesByIso2.put("VN", new CountryNames("Vietnam", "越南",""));
        countryNamesByIso2.put("VU", new CountryNames("Vanuatu", "瓦努阿图",""));
        countryNamesByIso2.put("WF", new CountryNames("Wallis a Futuna", "瓦利斯和富图纳",""));
        countryNamesByIso2.put("WS", new CountryNames("Samoa", "萨摩亚",""));
        countryNamesByIso2.put("XK", new CountryNames("Kosovo", null,""));
        countryNamesByIso2.put("YE", new CountryNames("Jemen", "也门",""));
        countryNamesByIso2.put("YT", new CountryNames("Mayotte", "马约特",""));
        countryNamesByIso2.put("ZA", new CountryNames("Jižní Afrika", "南非",""));
        countryNamesByIso2.put("ZM", new CountryNames("Zambie", "赞比亚",""));
        countryNamesByIso2.put("ZW", new CountryNames("Zimbabwe", "津巴布韦",""));
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
        return countriesApi.getAllCountries();
    }

    public String getCountryCzechName(String iso2) {
        return countryNamesByIso2.get(iso2).getCzechName();
    }

    public String getCountryChineseName(String iso2) {
        return countryNamesByIso2.get(iso2).getChineseName();
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

package SmbFile测试;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerStop {
	public static void main(String[] args) {
		int year = 0;
		int moth = 0;
		int day = 0;
		Calendar c = Calendar.getInstance();// 获得系统当前日期
		year = c.get(Calendar.YEAR);
		moth = c.get(Calendar.MONTH) + 1; // 系统日期从0开始算起
		day = c.get(Calendar.DAY_OF_MONTH);
		int hou = c.get(Calendar.HOUR);
		int xing = c.get(Calendar.HOUR_OF_DAY);
		int a = c.get(Calendar.DAY_OF_WEEK);

		System.out.println(year + "年" + moth + "月" + day + "日==" + hou + "==" + xing + "==" + a);
		Date currentTime = new Date();
		// SimpleDateFormat formatter = new
		// SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		System.out.println("123=========" + dateString);

		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		// SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("============" + dayOfWeek);
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		System.out.println(dayNames[dayOfWeek] + "====");

		Calendar cc = Calendar.getInstance();
		System.out.println();
		System.out.println("年：" + cc.get(Calendar.YEAR));
		System.out.println("月：" + (cc.get(Calendar.MONTH) + 1));
		System.out.println("日：" + cc.get(Calendar.DAY_OF_MONTH));
		System.out.println("24时制小时：" + cc.get(Calendar.HOUR_OF_DAY));
		System.out.println("12时制：" + cc.get(Calendar.HOUR));
		System.out.println("分：" + cc.get(Calendar.MINUTE));
		System.out.println("秒：" + cc.get(Calendar.SECOND));
		System.out.println("今天是一年中的第：" + cc.get(Calendar.DAY_OF_YEAR) + "天");

		// java.sql.Timestamp time = new java.sql.Timestamp(new
		// java.util.Date().getTime());
		// System.out.println(time.getHours()+"时"+time.getMinutes()+"分"+time.getSeconds()+"秒");//时
	}

}

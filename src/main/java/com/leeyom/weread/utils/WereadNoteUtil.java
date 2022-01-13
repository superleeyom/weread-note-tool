package com.leeyom.weread.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 微信读书笔记转换工具
 *
 * @author leeyom
 */
@Slf4j
public class WereadNoteUtil {

    /**
     * 换行符
     */
    private static final String BR_TAG = "\n";

    /**
     * 缩进符号
     */
    private static final String TAB_TAG = "\t";

    /**
     * 时间格式化
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 章节分割符号
     */
    private static final String CHAPTER_SPLIT_TAG = "◆";

    /**
     * 笔记分割符号
     */
    private static final String NOTE_SPLIT_TAG = ">>";

    /**
     * 将微信读书笔记转为markdown格式
     *
     * @param wereadNote 微信读书笔记
     * @return markdown格式
     */
    public static void convertWereadNote2Markdown(String wereadNote, HttpServletResponse response) {

        // markdown格式笔记
        StringBuilder markdown = new StringBuilder();

        // 按章节进行分割
        wereadNote = wereadNote.replaceAll("(\r)", "");
        String[] chapterArray = wereadNote.split(CHAPTER_SPLIT_TAG);

        // 书籍信息
        String[] bookInfoArray = chapterArray[0].split(BR_TAG);
        String bookName = StrUtil.isNotBlank(bookInfoArray[0]) ? bookInfoArray[0] : "无";
        String author = StrUtil.isNotBlank(bookInfoArray[1]) ? bookInfoArray[1] : "无";
        String noteNum = StrUtil.isNotBlank(bookInfoArray[2]) ? bookInfoArray[2] : "0";
        String noteDate = DATE_FORMATTER.format(LocalDateTime.now());
        String cover = getBookCover(bookName);

        markdown.append("> ");
        if (StrUtil.isNotBlank(cover)) {
            markdown.append(cover).append(BR_TAG);
        }
        markdown.append("作者：").append(author).append(BR_TAG)
                .append("时间：").append(noteDate).append(BR_TAG)
                .append("数量：").append(noteNum).append(BR_TAG).append(BR_TAG);

        // 读取每章节下面的笔记条目
        for (int i = 1; i < chapterArray.length; i++) {

            // 每章的笔记信息
            String[] noteArray = chapterArray[i].split(NOTE_SPLIT_TAG);
            String chapterName = noteArray[0].trim();
            markdown.append("- **").append(chapterName).append("：**").append(BR_TAG);

            // 笔记条目信息
            for (int j = 1; j < noteArray.length; j++) {
                String[] noteItemArray = noteArray[j].split(BR_TAG);
                for (String noteItem : noteItemArray) {
                    if (StrUtil.isBlank(noteItem)) {
                        continue;
                    }
                    markdown.append(TAB_TAG).append("- ").append(noteItem).append(BR_TAG);
                }
            }
            markdown.append(BR_TAG);
        }

        try {
            // 生成md文件
            File file = createMd(markdown, bookName);
            // 下载文件
            download(response, file);
        } catch (IOException e) {
            log.error("笔记下载失败：{}", e.getMessage());
        }
    }

    /**
     * 匹配书籍封面，利用微信读书暴露的公共API
     *
     * @param bookName 书籍名字
     * @return 封面url
     */
    private static String getBookCover(String bookName) {
        String requestUrl = "https://weread.qq.com/web/search/global?keyword=" + bookName + "&maxIdx=0&fragmentSize=120&count=20";
        String dataJson = HttpUtil.get(requestUrl);
        JSONObject data = JSONUtil.parseObj(dataJson);
        JSONArray books = data.getJSONArray("books");
        String cover = null;
        if (books != null && books.size() > 0) {
            for (Object b : books) {
                Map<String, Object> book = BeanUtil.beanToMap(b);
                Map<String, Object> bookInfo = BeanUtil.beanToMap(book.get("bookInfo"));
                if (bookName.equals(bookInfo.get("title"))) {
                    cover = (String) bookInfo.get("cover");
                    cover = "![" + bookName + "](" + cover + ")";
                    break;
                }
            }
            return cover;
        }
        return null;
    }

    private static void download(HttpServletResponse response, File file) throws IOException {
        // 以流的形式下载文件
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String(file.getName().getBytes("gbk"), "iso8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        // 清除临时文件
        file.delete();
    }

    private static File createMd(StringBuilder markdown, String bookName) throws IOException {
        String fileName = "[笔记]" + bookName + ".md";
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getName());
        fileWriter.write(markdown.toString());
        fileWriter.close();
        return file;
    }
}

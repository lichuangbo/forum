package cn.edu.tit.forum.cache;

import cn.edu.tit.forum.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
public class TagCache {
    public static List<TagDTO> get() {
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("JavaScript","jQuery", "CSS", "HTML", "Java", "PHP", "Python", "C", "C++", "C#", "Shell"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring", "Spring MVC", "Spring Boot", "Spring Cloud", "Django", "MyBatis", "Struts", "Vue.js", "Node.js", "Angular JS","React"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("VMware", "Linux", "Unix", "Ubuntu", "CentOS", "Nginx", "Docker", "Tomcat"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("SQL", "MySQL", "Oracle", "SQL Server", "DB2", "HBase", "MongoDb", "Redis"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("IDE", "Git", "SVN", "VSCode", "Vim", "Sublime", "IDEA", "Eclipse", "Pycharm", "Maven"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    /**
     * 过滤出非法的字符串
     *
     * @param tags 标签字符串
     * @return
     */
    public static String filterInvalid(String tags) {
        String[] split = tags.split(",");
        List<TagDTO> tagDTOS = get();

        // flatMap可以将两层的循环转化为一层
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = null;
        if (split.length != 0) {
            invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        }
        return invalid;
    }
}

package cn.edu.tit.forum.service;

import cn.edu.tit.forum.dto.NotifyDTO;
import cn.edu.tit.forum.dto.PageNationDTO;
import cn.edu.tit.forum.enums.NotifyStatusEnum;
import cn.edu.tit.forum.enums.NotifyTypeEnum;
import cn.edu.tit.forum.mapper.NotifyMapper;
import cn.edu.tit.forum.mapper.UserMapper;
import cn.edu.tit.forum.model.Notify;
import cn.edu.tit.forum.model.NotifyExample;
import cn.edu.tit.forum.model.User;
import exception.CustomizeErrorCode;
import exception.CustomizeException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2019/12/15
 */
@Service
public class NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private UserMapper userMapper;

    public PageNationDTO list(Long id, Integer page, Integer size) {
        PageNationDTO<NotifyDTO> pageNationDTO = new PageNationDTO<>();

        NotifyExample example = new NotifyExample();
        example.createCriteria()
                .andReceiverEqualTo(id);
        int totalCount = (int) notifyMapper.countByExample(example);

        int totalPage = (totalCount % size == 0) ? totalCount / size : totalCount / size + 1;
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        pageNationDTO.setPageNation(totalPage, page);

        int offset = size * (page - 1);
        NotifyExample example1 = new NotifyExample();
        example1.createCriteria()
                .andReceiverEqualTo(id);
        List<Notify> notifies = notifyMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        // 如果没有通知，返回空集合
        if (notifies.size() == 0) {
            return pageNationDTO;
        }
        // 有通知，将集合封装到pageNationDTO
        List<NotifyDTO> notifyDTOS = new ArrayList<>();
        for (Notify notify : notifies) {
            NotifyDTO notifyDTO = new NotifyDTO();
            BeanUtils.copyProperties(notify, notifyDTO);
            notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));

            notifyDTOS.add(notifyDTO);
        }
        pageNationDTO.setData(notifyDTOS);
        return pageNationDTO;
    }

    /**
     * 获取未读通知数
     *
     * @param userId 通知接收者ID
     * @return
     */
    public Long unreadCount(Long userId) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotifyStatusEnum.UNREAD.getStatus());
        return notifyMapper.countByExample(notifyExample);
    }

    /**
     * 读取通知
     *
     * @param id   通知ID
     * @param user User，貌似可以不用传    这里处理的异常不可能发生
     * @return
     */
    public NotifyDTO read(Long id, User user) {
        Notify notify = notifyMapper.selectByPrimaryKey(id);
        if (notify == null)
            throw new CustomizeException(CustomizeErrorCode.NOTIFY_NOT_FOUND);
        if (!notify.getReceiver().equals(user.getId()))
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFY_FAILE);

        notify.setStatus(NotifyStatusEnum.READ.getStatus());
        int i = notifyMapper.updateByPrimaryKey(notify);

        NotifyDTO notifyDTO = new NotifyDTO();
        BeanUtils.copyProperties(notify, notifyDTO);
        notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
        return notifyDTO;
    }
}

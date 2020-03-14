package cn.edu.tit.forum.service.impl;

import cn.edu.tit.forum.mapper.FavoriteMapper;
import cn.edu.tit.forum.model.Favorite;
import cn.edu.tit.forum.model.FavoriteExample;
import cn.edu.tit.forum.model.User;
import cn.edu.tit.forum.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichuangbo
 * @version 1.0
 * @created 2020/3/2
 */
@Service
public class FavoriteService implements IFavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public Favorite find(User user, Long articleId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andUserIdEqualTo(user.getId()).andArticleIdEqualTo(articleId);
        List<Favorite> favorites = favoriteMapper.selectByExample(favoriteExample);
        if (favorites == null || favorites.size() == 0) {
            return null;
        } else {
            return favorites.get(0);
        }
    }

    @Override
    public void insert(User user, Long articleId) {
        Favorite record = new Favorite();
        record.setArticleId(articleId);
        record.setUserId(user.getId());
        record.setGmtCreate(System.currentTimeMillis());
        favoriteMapper.insert(record);
    }

    @Override
    public void delete(User user, Long articleId) {
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andArticleIdEqualTo(articleId).andUserIdEqualTo(user.getId());
        favoriteMapper.deleteByExample(favoriteExample);
    }
}

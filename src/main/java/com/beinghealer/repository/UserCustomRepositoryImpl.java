package com.beinghealer.repository;

import com.beinghealer.model.QUser;
import com.beinghealer.model.UserStats;
import com.beinghealer.repository.helper.UserStatsQueryHelper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final QUser qUser = QUser.user;

    @Autowired
    public UserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Row> findOne(Long userId) {
        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qUser);
        final Tuple row = queryFactory.select(qUser, userStatsExpression)
                .from(qUser)
                .where(qUser.id.eq(userId))
                .fetchOne();
        return Optional.ofNullable(row).map(tuple ->
                Row.builder()
                        .user(tuple.get(qUser))
                        .userStats(tuple.get(userStatsExpression))
                        .build()
        );
    }


}

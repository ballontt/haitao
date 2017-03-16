namespace java com.haitao.service

typedef string Timestamp

struct TTbItemCat {
1: i64 parentId,
2: string name,
3: i32 status,
4: i32 sortOrder,
5: i32 isParent,
6: Timestamp created,
7: Timestamp updated,
}

service TTbItemCatService {
string queryTreeItemCat()
}


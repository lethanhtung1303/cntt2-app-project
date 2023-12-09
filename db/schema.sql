DROP TABLE IF EXISTS TDT_USER_PROFILE;
DROP TABLE IF EXISTS TDT_ROLE;
DROP TABLE IF EXISTS TDT_ROLE_CATEGORY;
DROP TABLE IF EXISTS TDT_NHAT_KY_GIANG_DAY;
DROP TABLE IF EXISTS TDT_CHUNG_CHI;
DROP TABLE IF EXISTS TDT_LOAI_CHUNG_CHI;
DROP TABLE IF EXISTS TDT_DIEM_HAI_LONG;
DROP TABLE IF EXISTS TDT_NGON_NGU_DAO_TAO;
DROP TABLE IF EXISTS TDT_QUA_TRINH_DAO_TAO;
DROP TABLE IF EXISTS TDT_LOAI_TOT_NGHIEP;
DROP TABLE IF EXISTS TDT_LICH_SU_GIANG_DAY;
DROP TABLE IF EXISTS TDT_GIANG_VIEN;
DROP TABLE IF EXISTS TDT_LOAI_GIANG_VIEN;
DROP TABLE IF EXISTS TDT_TRINH_DO;
DROP TABLE IF EXISTS TDT_NGON_NGU;
DROP TABLE IF EXISTS TDT_MON_HOC;
DROP TABLE IF EXISTS TDT_LOAI_MON;
DROP TABLE IF EXISTS TDT_NHOM_MON;
DROP TABLE IF EXISTS TDT_HOC_KY;
DROP TABLE IF EXISTS TDT_NAM_HOC;


CREATE TABLE TDT_nhom_mon (
	ma_nhom VARCHAR2(50 CHAR) NOT NULL,
	ten_nhom VARCHAR2(255 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_nhom_mon PRIMARY KEY (ma_nhom),
	CONSTRAINT UQ_nhom_mon_01 UNIQUE(ma_nhom)
);

INSERT INTO TDT_nhom_mon (ma_nhom,ten_nhom,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 ('0','Môn học bắt buộc','1',CURRENT_TIMESTAMP,'sys_user'),
	 ('7661_01_180502','Khóa luận tốt nghiệp','1',CURRENT_TIMESTAMP,'sys_user'),
	 ('7661_02_180502','Nhóm tự chọn chuyên ngành','1',CURRENT_TIMESTAMP,'sys_user'),
	 ('7657_180502','Nhóm tự chọn 1','1',CURRENT_TIMESTAMP,'sys_user'),
	 ('7658_180502','Nhóm tự chọn 2','1',CURRENT_TIMESTAMP,'sys_user'),
	 ('7659_180502','Nhóm tự chọn 3','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_loai_mon (
	ma_loai NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    phan_loai VARCHAR2(255 CHAR) NOT NULL,
    ky_hieu VARCHAR2(255 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT TDT_loai_mon PRIMARY KEY (ma_loai)
);

INSERT INTO TDT_loai_mon (ma_loai,phan_loai,ky_hieu,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (0,'Lý thuyết','LT',1,CURRENT_TIMESTAMP,'sys_user'),
	 (1,'Thực hành','TH',1,CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_mon_hoc (
	ma_mon VARCHAR2(50 CHAR) NOT NULL,
	ma_nhom VARCHAR2(50 CHAR) DEFAULT '0' NOT NULL,
	ten_mon VARCHAR2(255 CHAR) NOT NULL,
	so_tiet NUMBER(19,0) NOT NULL,
    ma_loai NUMBER(19,0) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_mon_hoc PRIMARY KEY (ma_mon),
	CONSTRAINT FK_mon_hoc_nhom_mon FOREIGN KEY (ma_nhom) REFERENCES TDT_nhom_mon(ma_nhom) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_mon_hoc_loai_mon FOREIGN KEY (ma_loai) REFERENCES TDT_loai_mon(ma_loai) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_mon_hoc (ma_mon,ma_nhom,ten_mon,so_tiet,ma_loai,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 ('501032_LT','0','Đại số tuyến tính cho Công nghệ thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501032_TH','0','Đại số tuyến tính cho Công nghệ thông tin',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501031_LT','0','Giải tích ứng dụng cho Công nghệ thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501031_TH','0','Giải tích ứng dụng cho Công nghệ thông tin',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501042_LT','0','Phương pháp lập trình',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501042_TH','0','Phương pháp lập trình',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501043_LT','0','Cấu trúc dữ liệu và giải thuật 1',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501043_TH','0','Cấu trúc dữ liệu và giải thuật 1',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502044_LT','0','Tổ chức máy tính',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502044_TH','0','Tổ chức máy tính',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502061_LT','0','Xác suất và thống kê ứng dụng cho Công nghệ thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502061_TH','0','Xác suất và thống kê ứng dụng cho Công nghệ thông tin',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501044_LT','0','Cấu trúc rời rạc',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('501044_TH','0','Cấu trúc rời rạc',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502047_LT','0','Nhập môn hệ điều hành',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502047_TH','0','Nhập môn hệ điều hành',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502046_LT','0','Nhập môn Mạng máy tính',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502046_TH','0','Nhập môn Mạng máy tính',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502051_LT','0','Hệ cơ sở dữ liệu',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502051_TH','0','Hệ cơ sở dữ liệu',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502041_LT','0','Phương pháp tính',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502041_TH','0','Phương pháp tính',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502056','0','Thực tập nghề nghiệp',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502042_LT','0','Toán tổ hợp và đồ thị',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502042_TH','0','Toán tổ hợp và đồ thị',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502045_LT','0','Công nghệ phần mềm',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502045_TH','0','Công nghệ phần mềm',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503040_LT','0','Phân tích và thiết kế giải thuật',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503040_TH','0','Phân tích và thiết kế giải thuật',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504078','0','Dự án Công nghệ thông tin 1',120,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504074','0','Kiến tập công nghiệp',120,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502CM1','0','Kỹ năng thực hành chuyên môn',0,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504079','7661_01_180502','Khóa luận tốt nghiệp',360,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504075','7661_02_180502','Dự án Công nghệ thông tin 2',90,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502043_LT','7657_180502','Cấu trúc dữ liệu và giải thuật 2',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502043_TH','7657_180502','Cấu trúc dữ liệu và giải thuật 2',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502050','7657_180502','Phân tích và thiết kế yêu cầu',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502057_LT','7657_180502','Nguyên lý ngôn ngữ lập trình',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502057_TH','7657_180502','Nguyên lý ngôn ngữ lập trình',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502068_LT','7657_180502','IoT cơ bản',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502068_TH','7657_180502','IoT cơ bản',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503056_LT','7657_180502','Phát triển phần mềm trên nền tảng tiến hóa',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503056_TH','7657_180502','Phát triển phần mềm trên nền tảng tiến hóa',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503058','7657_180502','Hệ thống hình thức và luận lý',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503074_LT','7657_180502','Phát triển ứng dụng di động',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503074_TH','7657_180502','Phát triển ứng dụng di động',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503107_LT','7657_180502','Phát triển ứng dụng di động nâng cao',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503107_TH','7657_180502','Phát triển ứng dụng di động nâng cao',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503108_LT','7657_180502','Thiết kế giao diện người dùng',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503108_TH','7657_180502','Thiết kế giao diện người dùng',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504058_LT','7657_180502','Kiểm thử phần mềm',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504058_TH','7657_180502','Kiểm thử phần mềm',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503044','7658_180502','Nhập môn Học máy',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503045','7658_180502','Truy hồi thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503057_LT','7658_180502','Công nghệ phần mềm trên nền tảng ứng dụng hiện đại',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503057_TH','7658_180502','Công nghệ phần mềm trên nền tảng ứng dụng hiện đại',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503073_LT','7658_180502','Lập trình web và ứng dụng',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503073_TH','7658_180502','Lập trình web và ứng dụng',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503075_LT','7658_180502','An toàn mạng không dây và di động',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503075_TH','7658_180502','An toàn mạng không dây và di động',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503079','7658_180502','Lý thuyết mật mã',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503106_LT','7658_180502','Lập trình web nâng cao',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503106_TH','7658_180502','Lập trình web nâng cao',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504045','7658_180502','Nhập môn xử lý ngôn ngữ tự nhiên',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504048','7658_180502','Xử lý dữ liệu lớn',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504049','7658_180502','Hệ thống thương mại thông minh',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504051_LT','7658_180502','Mạng đa phương tiện và di động',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504051_TH','7658_180502','Mạng đa phương tiện và di động',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504060_LT','7658_180502','Kiểm chứng và thẩm định phần mềm',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504060_TH','7658_180502','Kiểm chứng và thẩm định phần mềm',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504070','7658_180502','Kiến trúc hướng dịch vụ',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504073_LT','7658_180502','Chuyên đề Công nghệ phần mềm',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504073_TH','7658_180502','Chuyên đề Công nghệ phần mềm',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504076_LT','7658_180502','Phát triển trò chơi',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504076_TH','7658_180502','Phát triển trò chơi',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504077_LT','7658_180502','Mẫu thiết kế',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504077_TH','7658_180502','Mẫu thiết kế',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505041','7658_180502','Nhập môn xử lý tiếng nói',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505043','7658_180502','Khai thác dữ liệu và Khai phá tri thức',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505045','7658_180502','Mô hình không chắc chắn',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505051','7658_180502','Nhập môn các hệ thống phân tán',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505053','7658_180502','Thẩm định phần mềm tự động',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505054','7658_180502','Kỹ thuật thiết kế và đặc tả hình thức',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505055_LT','7658_180502','Thiết kế phần mềm nhúng',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505055_TH','7658_180502','Thiết kế phần mềm nhúng',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505060_LT','7658_180502','Nhập môn Xử lý ảnh số',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505060_TH','7658_180502','Nhập môn Xử lý ảnh số',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('505061','7658_180502','Hệ thống phát hiện xâm nhập mạng',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502048','7659_180502','Nhập môn tính toán đa phương tiện',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502049','7659_180502','Nhập môn Bảo mật thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502052_LT','7659_180502','Phát triển hệ thống thông tin doanh nghiệp',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('502052_TH','7659_180502','Phát triển hệ thống thông tin doanh nghiệp',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503043','7659_180502','Nhập môn Trí tuệ nhân tạo',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503050_LT','7659_180502','Giao thức và Mạng máy tính',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503050_TH','7659_180502','Giao thức và Mạng máy tính',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503052_LT','7659_180502','Lập trình song song và đồng thời',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503052_TH','7659_180502','Lập trình song song và đồng thời',30,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503066','7659_180502','Hệ thống hoạch định nguồn lực doanh nghiệp',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503067','7659_180502','Công nghệ thông tin trong Quản lý quan hệ khách hàng',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('503109','7659_180502','Quản trị hệ thống thông tin',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504068','7659_180502','Cơ sở dữ liệu phân tán',45,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504088_LT','7659_180502','Nhập môn Bảo mật máy tính',30,0,'1',CURRENT_TIMESTAMP,'sys_user'),
	 ('504088_TH','7659_180502','Nhập môn Bảo mật máy tính',30,1,'1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_loai_giang_vien (
	ma_loai NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    phan_loai VARCHAR2(255 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT PK_loai_giang_vien PRIMARY KEY (ma_loai)
);

INSERT INTO TDT_loai_giang_vien (ma_loai,phan_loai,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (0,'Giảng viên thỉnh giảng',1,CURRENT_TIMESTAMP,'sys_user'),
	 (1,'Giảng viên cơ hữu',1,CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_giang_vien (
	ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    FIRST_NAME VARCHAR2(50 CHAR)  NULL,
    FULL_NAME VARCHAR2(100 CHAR) NOT NULL,
    GENDER CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    IMAGES VARCHAR2(255 CHAR) NULL,
    BIRTHDAY DATE NULL,
	PLACE_OF_BIRTH VARCHAR2(50 CHAR) NULL,
	ADDRESS VARCHAR2(100 CHAR) NULL,
    ADDRESS_TMP VARCHAR2(100 CHAR) NULL,
    PHONE VARCHAR2(20 CHAR) NULL,
    EMAIL VARCHAR2(50 CHAR) NULL,
	EMAIL_TDTU VARCHAR2(255 CHAR),
	WORKPLACE VARCHAR2(255 CHAR),
	MAIN_POSITION VARCHAR2(255 CHAR),
	SECONDARY_POSITION VARCHAR2(255 CHAR),
	CLASSIFICATION_LECTURERS NUMBER(19,0) DEFAULT 1 NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	DELETED_FLAG CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    DELETED_AT DATE DEFAULT NULL,
    DELETED_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_giang_vien PRIMARY KEY (ID),
	CONSTRAINT FK_giang_vien_loai_giang_vien FOREIGN KEY (CLASSIFICATION_LECTURERS) REFERENCES TDT_loai_giang_vien(ma_loai) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT UQ_giang_vien_01 UNIQUE(EMAIL_TDTU)
);

INSERT INTO TDT_giang_vien (ID,FIRST_NAME,FULL_NAME,GENDER,IMAGES,BIRTHDAY,PLACE_OF_BIRTH,ADDRESS,ADDRESS_TMP,PHONE,EMAIL,EMAIL_TDTU,WORKPLACE,MAIN_POSITION,SECONDARY_POSITION,CLASSIFICATION_LECTURERS,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
    (1,'Hà Phú','Toàn','1','https://mir-s3-cdn-cf.behance.net/project_modules/fs/7383d138650505.598fa11956e27.jpg','1991-08-19 00:00:00.000','An Long, Tam Nông,Đồng Tháp','Đường Tống Văn Lê , Ấp Tân Phú 2 , xã Tân Lý Đông','Nguyễn văn Tăng, Quận 9 , TPHCM','0955667788','haphutoan@gmail.com','haphutoan@tdtu.edu.vn','TDTU',NULL,NULL,1,'1','2023-11-04 21:45:46.000','sys_user'),
    (2,'Lê Thị Cẩm','Tú','0','https://mir-s3-cdn-cf.behance.net/project_modules/fs/3671da38650505.598fa119575fb.jpg','1991-08-14 00:00:00.000','Tiền Giang','Cai lậy - Tiền Giang','Tân Uyên, Bình Dương','0366828282','tu@gmail.com','tu@tdtu.edu.vn','CTU',NULL,NULL,1,'1','2023-11-04 21:45:46.000','sys_user'),
    (3,'Nguyễn Thị Kim','Thanh','0','https://mir-s3-cdn-cf.behance.net/project_modules/fs/1454d038650505.598fa118c9674.jpg','1991-06-22 00:00:00.000','Đồng Nai','Đồng Nai','Long Bình - Đồng Nai','0123123123','thanhn@gmail.com','thanh@tdtu.edu.vn','RMIT',NULL,NULL,0,'1','2023-11-04 21:45:46.000','sys_user'),
    (4,'Nguyễn Thị Thu','Kim','0','https://mir-s3-cdn-cf.behance.net/project_modules/fs/e13eb438650505.598fa118c8eab.jpg','1991-04-18 00:00:00.000','Đồng Nai','Biên Hòa - Đồng Nai','Tân Uyên, Bình Dương','321634645','canhhongruclua@gmail.com','canhhongruclua@tdtu.edu.vn','UFM',NULL,NULL,1,'1','2023-11-04 21:45:46.000','sys_user'),
    (5,'Vũ Quốc','Hòa','0','https://mir-s3-cdn-cf.behance.net/project_modules/fs/0bdadd38650505.598fa118c9a00.jpg','1991-10-09 00:00:00.000','Hải Dương','Long Bình - Biên Hòa - Đồng Nai','Tân Uyên, Bình Dương','0972838671','hoa@gmail.com','hoan@tdtu.edu.vn','TDTU',NULL,NULL,1,'1','2023-11-04 21:45:46.000','sys_user'),
    (6,'Lê','Minh Châu','0','https://mir-s3-cdn-cf.behance.net/project_modules/fs/14809638650505.598fa118c91f3.jpg','1991-08-25 00:00:00.000','Đồng Nai','Đường Tống Văn Lê , Ấp Tân Phú 2 , xã Tân Lý Đông','Tân Uyên, Bình Dương','0972838671','haphutoanit@gmail.com','haphutoanit@tdtu.edu.vn','TDTU',NULL,NULL,1,'1','2023-11-04 21:45:46.000','sys_user'),
    (7,'Nguyễn Hoàng','Tú','1','https://mir-s3-cdn-cf.behance.net/project_modules/fs/db780138650505.598fa11956761.jpg','1990-05-17 00:00:00.000','Đồng Nai','Đường Tống Văn Lê , Ấp Tân Phú 2 , xã Tân Lý Đông','Tân Uyên, Bình Dương','03344558832','hatheduy@gmail.com','hatheduy@tdtu.edu.vn','TDTU',NULL,NULL,0,'1','2023-11-04 21:45:46.000','sys_user'),
    (8,'Hoàng Bảo','Ngọc','1','https://mir-s3-cdn-cf.behance.net/project_modules/fs/fd44d538650505.598fa11957245.jpg','1993-11-04 00:00:00.000','Đồng Nai','Biên Hòa - Đồng Nai','Tân Uyên, Bình Dương','01688786834','itw@gmail.com','itw@tdtu.edu.vn','TDTU',NULL,NULL,0,'1','2023-11-04 21:45:46.000','sys_user');

CREATE TABLE TDT_trinh_do (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
	trinh_do VARCHAR2(255 CHAR) NOT NULL,
	ky_hieu VARCHAR2(255 CHAR) NOT NULL,
    display_order  NUMBER(19,0) DEFAULT 9999 NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_cap_bac PRIMARY KEY (id),
	CONSTRAINT UQ_cap_bac_01 UNIQUE(ky_hieu)
);

INSERT INTO TDT_trinh_do (id,trinh_do,ky_hieu,display_order,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (6,'Cử nhân','CN',7,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (7,'Kỹ sư','KS',6,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,'Nghiên cứu sinh','NCS',5,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,'Thạc sĩ','Ths',4,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,'Tiến sĩ','TS',3,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,'Phó giáo sư','PGS',2,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (1,'Giáo sư','GS',1,'1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_loai_tot_nghiep (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    loai_tot_nghiep VARCHAR2(255 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_loai_tot_nghiep PRIMARY KEY (id),
	CONSTRAINT UQ_loai_tot_nghiep_01 UNIQUE(loai_tot_nghiep)
);

INSERT INTO TDT_loai_tot_nghiep (id,loai_tot_nghiep,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (1,'Xuất sắc','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,'Giỏi','1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,'Khá','1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,'Trung bình','1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,'Yếu','1',CURRENT_TIMESTAMP,'sys_user'),
	 (6,'Kém','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_qua_trinh_dao_tao (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
	giang_vien_id NUMBER(19,0) NOT NULL,
    trinh_do_id NUMBER(19,0) DEFAULT 7 NOT NULL,
	truong VARCHAR2(255 CHAR),
	nganh VARCHAR2(255 CHAR),
	nam_tot_nghiep NUMBER(19,0),
	de_tai_tot_nghiep VARCHAR2(255 CHAR),
	nguoi_huong_dan VARCHAR2(255 CHAR),
	loai_tot_nghiep_id NUMBER(19,0) DEFAULT 0 NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_qua_trinh_dao_tao PRIMARY KEY (id),
	CONSTRAINT FK_qua_trinh_dao_tao_giang_vien FOREIGN KEY (giang_vien_id) REFERENCES TDT_giang_vien(ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_qua_trinh_dao_tao_trinh_do FOREIGN KEY (trinh_do_id) REFERENCES TDT_trinh_do(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_qua_trinh_dao_tao_loai_tot_nghiep FOREIGN KEY (loai_tot_nghiep_id) REFERENCES TDT_loai_tot_nghiep(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_qua_trinh_dao_tao (id, giang_vien_id, trinh_do_id, truong, nganh, nam_tot_nghiep, de_tai_tot_nghiep, nguoi_huong_dan, loai_tot_nghiep_id, IS_ACTIVE, CREATED_AT, CREATED_BY)VALUES
	 (1,1,7,'Đại học ABC','Công nghệ',2010,NULL,NULL,2,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,2,7,'Đại học XYZ','Khoa học',2015,NULL,NULL,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,3,7,'Đại học DEF','Quản trị',2013,NULL,NULL,1,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,4,7,'Đại học ABC','Ngôn ngữ',2008,NULL,NULL,2,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,5,7,'Đại học XYZ','Ngoại ngữ',2011,NULL,NULL,3,'1',CURRENT_TIMESTAMP,'sys_user'),
     (6,1,6,'Đại học 6','Công nghệ 1',2010,NULL,NULL,2,'1',CURRENT_TIMESTAMP,'sys_user'),
     (7,1,5,'Đại học 7','Công nghệ 2',2010,NULL,NULL,2,'1',CURRENT_TIMESTAMP,'sys_user'),
     (8,2,4,'Đại học 8','Khoa học 2',2015,NULL,NULL,1,'1',CURRENT_TIMESTAMP,'sys_user'),
     (9,2,3,'Đại học 9','Khoa học 4',2015,NULL,NULL,1,'1',CURRENT_TIMESTAMP,'sys_user'),
     (10,3,2,'Đại học 10','Khoa học 5',2015,NULL,NULL,1,'1',CURRENT_TIMESTAMP,'sys_user'),
     (11,4,1,'Đại học Ngoại ngữ','Ngoại ngữ',2011,NULL,NULL,3,'1',CURRENT_TIMESTAMP,'sys_user'),
     (12,6,1,'Đại học Ngoại ngữ','Ngoại ngữ',2011,NULL,NULL,3,'1',CURRENT_TIMESTAMP,'sys_user'),
     (13,6,2,'Đại học Ngoại ngữ','Ngoại ngữ',2011,NULL,NULL,3,'1',CURRENT_TIMESTAMP,'sys_user'),
     (14,6,7,'Đại học Ngoại ngữ','Ngoại ngữ',2011,NULL,NULL,3,'1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_ngon_ngu (
    id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    ten_ngon_ngu VARCHAR2(50 CHAR) NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT PK_ngon_ngu PRIMARY KEY(id)
);

INSERT INTO TDT_ngon_ngu (id,ten_ngon_ngu,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
    (1,'Việt','1',CURRENT_TIMESTAMP,'sys_user'),
    (2,'Anh','1',CURRENT_TIMESTAMP,'sys_user'),
    (3,'Nhật','1',CURRENT_TIMESTAMP,'sys_user'),
    (4,'Pháp','1',CURRENT_TIMESTAMP,'sys_user'),
    (5,'Trung','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_ngon_ngu_dao_tao (
    id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    qua_trinh_dao_tao_id NUMBER(19,0)  NOT NULL,
    ngon_ngu_id NUMBER(19,0) DEFAULT 1 NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT PK_ngon_ngu_dao_tao PRIMARY KEY(id),
    CONSTRAINT FK_ngon_ngu_dao_tao_qua_trinh_dao_tao FOREIGN KEY (qua_trinh_dao_tao_id) REFERENCES TDT_qua_trinh_dao_tao(id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT FK_ngon_ngu_dao_tao_ngon_ngu FOREIGN KEY (ngon_ngu_id) REFERENCES TDT_ngon_ngu(id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_ngon_ngu_dao_tao (ID,qua_trinh_dao_tao_id,ngon_ngu_id,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
    (1,1,1,'1',CURRENT_TIMESTAMP,'sys_user'),
    (2,1,2,'1',CURRENT_TIMESTAMP,'sys_user'),
    (3,1,3,'1',CURRENT_TIMESTAMP,'sys_user'),
    (4,1,4,'1',CURRENT_TIMESTAMP,'sys_user'),
    (5,2,1,'1',CURRENT_TIMESTAMP,'sys_user'),
    (6,2,2,'1',CURRENT_TIMESTAMP,'sys_user'),
    (7,3,2,'1',CURRENT_TIMESTAMP,'sys_user'),
    (8,3,5,'1',CURRENT_TIMESTAMP,'sys_user'),
    (9,4,3,'1',CURRENT_TIMESTAMP,'sys_user'),
    (10,4,5,'1',CURRENT_TIMESTAMP,'sys_user'),
    (11,5,1,'1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_loai_chung_chi (
	ma_loai NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    phan_loai VARCHAR2(255 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT PK_loai_chung_chi PRIMARY KEY (ma_loai)
);

INSERT INTO TDT_loai_chung_chi (ma_loai,phan_loai,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (1,'IELTS',1,CURRENT_TIMESTAMP,'sys_user'),
	 (2,'TOEFL iBT',1,CURRENT_TIMESTAMP,'sys_user'),
	 (3,'TOEIC',1,CURRENT_TIMESTAMP,'sys_user'),
	 (4,'Cambridge',1,CURRENT_TIMESTAMP,'sys_user'),
	 (5,'Aptis ESOL',1,CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_chung_chi(
    id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    giang_vien_id NUMBER(19,0),
    loai_chung_chi NUMBER(19,0) NOT NULL,
    diem VARCHAR2(255 CHAR),
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT CONSTRAINT_chung_chi PRIMARY KEY (id),
    CONSTRAINT CONSTRAINT_FK_chung_chi_giang_vien FOREIGN KEY (giang_vien_id) REFERENCES TDT_giang_vien(ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT CONSTRAINT_FK_chung_chi_loai_chung_chi FOREIGN KEY (loai_chung_chi) REFERENCES TDT_loai_chung_chi(ma_loai) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE TDT_nam_hoc(
    nam_hoc NUMBER(19,0) NOT NULL,
    nam_hoc_labels VARCHAR2(50 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT CONSTRAINT_nam_hoc PRIMARY KEY (nam_hoc)
);

INSERT INTO TDT_nam_hoc (nam_hoc,nam_hoc_labels,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (2018,'2018-2019','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2019,'2019-2020','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2020,'2020-2021','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2021,'2021-2022','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2022,'2022-2023','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2023,'2023-2024','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_hoc_ky(
    nam_hoc NUMBER(19,0) NOT NULL,
    hoc_ky NUMBER(19,0) NOT NULL,
    hoc_ky_labels VARCHAR2(50 CHAR) NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
    CONSTRAINT CONSTRAINT_hoc_ky PRIMARY KEY (hoc_ky),
    CONSTRAINT FK_hoc_ky_nam_hoc FOREIGN KEY (nam_hoc) REFERENCES TDT_nam_hoc(nam_hoc) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_hoc_ky (nam_hoc,hoc_ky,hoc_ky_labels,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
     (2018,201801,'Học kỳ 1/ 2018-2019','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2018,201802,'Học kỳ 2/ 2018-2019','1',CURRENT_TIMESTAMP,'sys_user'),
     (2018,201803,'Học kỳ hè/ 2018-2019','1',CURRENT_TIMESTAMP,'sys_user'),

     (2019,201901,'Học kỳ 1/ 2019-2020','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2019,201902,'Học kỳ 2/ 2019-2020','1',CURRENT_TIMESTAMP,'sys_user'),
     (2019,201903,'Học kỳ hè/ 2019-2020','1',CURRENT_TIMESTAMP,'sys_user'),

     (2020,202001,'Học kỳ 1/ 2020-2021','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2020,202002,'Học kỳ 2/ 2020-2021','1',CURRENT_TIMESTAMP,'sys_user'),
     (2020,202003,'Học kỳ hè/ 2020-2021','1',CURRENT_TIMESTAMP,'sys_user'),

	 (2021,202101,'Học kỳ 1/ 2021-2022','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2021,202102,'Học kỳ 2/ 2021-2022','1',CURRENT_TIMESTAMP,'sys_user'),
     (2021,202103,'Học kỳ hè/ 2021-2022','1',CURRENT_TIMESTAMP,'sys_user'),

	 (2022,202201,'Học kỳ 1/ 2022-2023','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2022,202202,'Học kỳ 2/ 2022-2023','1',CURRENT_TIMESTAMP,'sys_user'),
     (2022,202203,'Học kỳ hè/ 2022-2023','1',CURRENT_TIMESTAMP,'sys_user'),

	 (2023,202301,'Học kỳ 1/ 2023-2024','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2023,202302,'Học kỳ 2/ 2023-2024','1',CURRENT_TIMESTAMP,'sys_user'),
     (2023,202303,'Học kỳ hè/ 2023-2024','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_diem_hai_long (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
	giang_vien_id NUMBER(19,0),
    ma_mon VARCHAR2(50 CHAR),
	hoc_ky NUMBER(19,0),
	diem_hai_long NUMBER(19,0),
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_diem_hai_long PRIMARY KEY (id),
	CONSTRAINT FK_diem_hai_long_giang_vien FOREIGN KEY (giang_vien_id) REFERENCES TDT_giang_vien(ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_diem_hai_long_mon_hoc FOREIGN KEY (ma_mon) REFERENCES TDT_mon_hoc(ma_mon) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_diem_hai_long_hoc_ky FOREIGN KEY (hoc_ky) REFERENCES TDT_hoc_ky(hoc_ky) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_diem_hai_long (id,giang_vien_id,ma_mon,hoc_ky,diem_hai_long,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (1,1,'504078',202301,5,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,2,'504074',202301,4,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,3,'502CM1',202302,5,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,4,'504074',202302,4,'1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,5,'502CM1',202303,5,'1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_lich_su_giang_day (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
	giang_vien_id NUMBER(19,0),
    hoc_ky NUMBER(19,0),
    ma_mon VARCHAR2(255 CHAR),
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_lich_su_giang_day PRIMARY KEY (id),
	CONSTRAINT FK_lich_su_giang_day_giang_vien FOREIGN KEY (giang_vien_id) REFERENCES TDT_giang_vien(ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_lich_su_giang_day_hoc_ky FOREIGN KEY (hoc_ky) REFERENCES TDT_hoc_ky(hoc_ky) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FK_lich_su_giang_day_mon_hoc FOREIGN KEY (ma_mon) REFERENCES TDT_mon_hoc(ma_mon) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_lich_su_giang_day (id,giang_vien_id,hoc_ky,ma_mon,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (1,1,202301,'502043_LT','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,2,202301,'502056','1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,3,202302,'502051_TH','1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,4,202302,'502051_LT','1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,5,202303,'502046_LT','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_nhat_ky_giang_day (
	id NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY (START WITH 100 INCREMENT BY 3) NOT NULL,
    lich_su_giang_day_id NUMBER(19,0),
	bao_vang NUMBER(19,0),
	bao_bu NUMBER(19,0),
	giam_thi_nhac_nho NUMBER(19,0),
	di_tre NUMBER(19,0),
	ve_som NUMBER(19,0),
	tac_phong NUMBER(19,0),
	cham_diem_sai NUMBER(19,0),
	nop_bai_tre NUMBER(19,0),
	phuc_khao NUMBER(19,0),
	dau_nhieu CHAR(1 CHAR) DEFAULT '0' NOT NULL,
	rot_nhieu CHAR(1 CHAR) DEFAULT '0' NOT NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	CONSTRAINT PK_nhat_ky_giang_day PRIMARY KEY (id),
	CONSTRAINT FK_nhat_ky_giang_day_lich_su_giang_day FOREIGN KEY (lich_su_giang_day_id) REFERENCES TDT_lich_su_giang_day(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO TDT_nhat_ky_giang_day (id,lich_su_giang_day_id,bao_vang,bao_bu,giam_thi_nhac_nho,di_tre,ve_som,tac_phong,cham_diem_sai,nop_bai_tre,phuc_khao,dau_nhieu,rot_nhieu,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
	 (1,1,0,1,0,1,0,0,2,0,1,'0','1','1',CURRENT_TIMESTAMP,'sys_user'),
	 (2,2,1,0,0,0,0,1,1,0,0,'0','0','1',CURRENT_TIMESTAMP,'sys_user'),
	 (3,3,0,0,1,0,0,0,0,1,0,'1','0','1',CURRENT_TIMESTAMP,'sys_user'),
	 (4,4,0,0,0,0,0,0,0,0,0,'0','0','1',CURRENT_TIMESTAMP,'sys_user'),
	 (5,5,0,1,0,0,0,0,0,0,0,'0','0','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_ROLE_CATEGORY(
	ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY  (START WITH 100 INCREMENT BY 3)  NOT NULL,
    CATEGORY_NAME_VN VARCHAR2(50 CHAR) NULL,
    CATEGORY_NAME_EN VARCHAR2(50 CHAR) NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	PRIMARY KEY(ID)
);

INSERT INTO TDT_ROLE_CATEGORY (ID,CATEGORY_NAME_VN,CATEGORY_NAME_EN,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
    (1,'Trang chủ','HomePage','1',CURRENT_TIMESTAMP,'sys_user'),
    (2,'Nhân sự','HR','1',CURRENT_TIMESTAMP,'sys_user'),
    (3,'Danh mục nhân sự','HR Category','1',CURRENT_TIMESTAMP,'sys_user'),
    (4,'Lương','Salary','1',CURRENT_TIMESTAMP,'sys_user'),
    (5,'Danh mục Lương','Salary Category','1',CURRENT_TIMESTAMP,'sys_user'),
    (6,'Hợp đồng','Contract','1',CURRENT_TIMESTAMP,'sys_user'),
    (7,'Danh mục hợp đồng','Contract Category','1',CURRENT_TIMESTAMP,'sys_user'),
    (8,'Hệ thống','System','1',CURRENT_TIMESTAMP,'sys_user');

CREATE TABLE TDT_ROLE(
	ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY  (START WITH 100 INCREMENT BY 3)  NOT NULL,
    EMPLOYEE_ID NUMBER(19,0) NULL,
    CATEGORY_ROLE NUMBER(19,0) NULL,
    ROLE_VIEW CHAR(1 CHAR) DEFAULT '0' NULL,
	ROLE_UPDATE CHAR(1 CHAR) DEFAULT '0' NULL,
	ROLE_DELETE CHAR(1 CHAR) DEFAULT '0' NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	PRIMARY KEY(ID)
);


ALTER TABLE TDT_ROLE
    ADD CONSTRAINT FK_ROLE_ROLE_CATEGORY FOREIGN KEY(CATEGORY_ROLE)
        REFERENCES TDT_ROLE_CATEGORY(ID)
;

ALTER TABLE TDT_ROLE
    ADD CONSTRAINT FK_ROLE_INSTRUCTOR FOREIGN KEY(EMPLOYEE_ID)
        REFERENCES TDT_giang_vien(ID)
;

INSERT INTO TDT_ROLE (ID,EMPLOYEE_ID,CATEGORY_ROLE,ROLE_VIEW,ROLE_UPDATE,ROLE_DELETE,IS_ACTIVE,CREATED_AT,CREATED_BY) VALUES
    (1,1,1,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (2,2,1,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (3,1,6,'1','1','1','0',CURRENT_TIMESTAMP,'sys_user'),
    (4,1,7,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (5,1,5,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (6,1,3,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (7,1,8,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (8,1,4,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (9,1,2,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (10,2,2,'1','1','1','1',CURRENT_TIMESTAMP,'sys_user'),
    (11,2,3,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user'),
    (12,2,4,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user'),
    (13,2,5,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user'),
    (14,2,6,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user'),
    (15,2,7,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user'),
    (16,2,8,'1','0','0','1',CURRENT_TIMESTAMP,'sys_user');


CREATE TABLE TDT_USER_PROFILE(
	USER_ID NUMBER(19,0) GENERATED BY DEFAULT AS IDENTITY  (START WITH 100 INCREMENT BY 3)  NOT NULL,
    USER_NAME VARCHAR2(100 CHAR) NULL,
    EMPLOYEE_ID NUMBER(19,0) NULL,
    PASSWORD VARCHAR2(255 CHAR) NULL,
    ROLE_LIST VARCHAR2(50 CHAR) NULL,
    IS_MANAGEMENT CHAR(1 CHAR) DEFAULT '0' NULL,
	ACCOUNT_STATUS NUMBER(19,0) DEFAULT 0 NULL,
	IP_LAST_LOGIN VARCHAR2(50 CHAR) NULL,
	DATE_LAST_LOGIN DATE NULL,
	TIME_NEXT_LOGIN DATE NULL,
	COUNT_LOGIN_FAILED NUMBER(19,0) NULL,
    IS_ACTIVE CHAR(1 CHAR) DEFAULT '1' NOT NULL,
    CREATED_AT DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CREATED_BY  VARCHAR2(255 CHAR) NOT NULL,
    UPDATED_AT DATE DEFAULT NULL,
    UPDATE_BY VARCHAR2(255 CHAR) DEFAULT NULL,
	PRIMARY KEY(USER_ID)
);

ALTER TABLE TDT_USER_PROFILE
    ADD CONSTRAINT FK_USERPROFILE_INSTRUCTOR FOREIGN KEY(EMPLOYEE_ID)
        REFERENCES TDT_giang_vien(ID)
;

INSERT INTO TDT_USER_PROFILE (USER_ID,USER_NAME,EMPLOYEE_ID,PASSWORD,IS_ACTIVE,ROLE_LIST,IS_MANAGEMENT,ACCOUNT_STATUS,IP_LAST_LOGIN,DATE_LAST_LOGIN,TIME_NEXT_LOGIN,COUNT_LOGIN_FAILED,CREATED_AT,CREATED_BY) VALUES
    (1,'admin',1,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','1',NULL,'1',0,'192.168.1.160','2023-10-12 23:20:59.000',NULL,1,CURRENT_TIMESTAMP,'sys_user'),
    (2,'minht',3,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','1',NULL,'0',0,NULL,NULL,NULL,1,CURRENT_TIMESTAMP,'sys_user'),
    (3,'tultc',2,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','1',NULL,'0',0,NULL,NULL,NULL,NULL,CURRENT_TIMESTAMP,'sys_user'),
    (4,'duyht',4,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','1',NULL,'0',0,NULL,NULL,NULL,NULL,CURRENT_TIMESTAMP,'sys_user'),
    (5,'toanhp',8,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','1',NULL,'0',0,'192.168.1.5','2023-08-26 11:18:34.000',NULL,NULL,CURRENT_TIMESTAMP,'sys_user'),
    (6,'admin1',1,'$2a$10$xnUvVK8sV07imyzKTBnaWuNmiy7T1Dxhd6kWilR4GkMFQ0UcqHhxy','0',NULL,'0',0,'192.168.1.5','2023-08-26 11:26:09.000',NULL,NULL,CURRENT_TIMESTAMP,'sys_user');
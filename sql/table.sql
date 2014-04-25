--
-- Table structure for table `flash`
--

CREATE TABLE IF NOT EXISTS `flash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `knowledge` varchar(500) NOT NULL,
  `url` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `knowledge` varchar(500) NOT NULL,
  `url` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `knowledge`
--

CREATE TABLE IF NOT EXISTS `knowledge` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `explanation` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `problem`
--

CREATE TABLE IF NOT EXISTS `problem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_type` varchar(20) NOT NULL,
  `difficulty` int(11) NOT NULL,
  `problem_content` text NOT NULL,
  `key_content` text NOT NULL,
  `knowledge` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `problem_resource`
--

CREATE TABLE IF NOT EXISTS `problem_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8 NOT NULL,
  `uri` varchar(1000) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `resource`
--

CREATE TABLE IF NOT EXISTS `resource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `media_type` varchar(20) CHARACTER SET utf8 NOT NULL,
  `file_format` varchar(20) CHARACTER SET utf8 NOT NULL,
  `file_size` double NOT NULL,
  `link_type` varchar(20) CHARACTER SET utf8 NOT NULL,
  `url` varchar(1000) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE IF NOT EXISTS `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `description` text NOT NULL,
  `subject_name` varchar(500) NOT NULL,
  `url` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
);

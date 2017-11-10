SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `webapp2`
--

-- --------------------------------------------------------

--
-- Table structure for table `jobmodel`
--

CREATE TABLE IF NOT EXISTS `jobmodel` (
`id` int(11) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `workplace` varchar(255) NOT NULL,
  `workaddress` varchar(255) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `phonebookmodel`
--

CREATE TABLE IF NOT EXISTS `phonebookmodel` (
`id` int(11) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `firstname` varchar(10) NOT NULL,
  `workphone` varchar(16) NOT NULL,
  `mobilephone` varchar(16) NOT NULL,
  `email` varchar(41) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `work` varchar(255) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jobmodel`
--
ALTER TABLE `jobmodel`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `phonebookmodel`
--
ALTER TABLE `phonebookmodel`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jobmodel`
--
ALTER TABLE `jobmodel`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
--
-- AUTO_INCREMENT for table `phonebookmodel`
--
ALTER TABLE `phonebookmodel`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
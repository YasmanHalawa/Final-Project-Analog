-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 15 Jun 2021 pada 16.00
-- Versi server: 10.4.17-MariaDB
-- Versi PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `database.java_gui`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tabel_gaji`
--

CREATE TABLE `tabel_gaji` (
  `Nik` int(20) NOT NULL,
  `Nama` varchar(30) NOT NULL,
  `Jabatan` varchar(30) NOT NULL,
  `Gapok` int(20) NOT NULL,
  `Transport` int(20) NOT NULL,
  `Gaber` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tabel_gaji`
--

INSERT INTO `tabel_gaji` (`Nik`, `Nama`, `Jabatan`, `Gapok`, `Transport`, `Gaber`) VALUES
(12345, 'Yasman Halawa', 'Manager', 5000000, 500000, 5500000),
(123455, 'Uliana Halawa', 'Asisten Manager', 4500000, 450000, 4950000),
(123456, 'Anggi Marito', 'Kepala HRD', 4000000, 400000, 4400000),
(1234567, 'Batman Suparman', 'Karyawan', 3000000, 300000, 3300000),
(12345677, 'Mister Love', 'Office Boy', 2500000, 250000, 2750000),
(12345678, 'Mike Litoriswswwww', 'Kepala HRD', 4000000, 400000, 4400000),
(123456777, 'nullrrrreAAA', 'Asisten Manager', 4500000, 450000, 4950000),
(1234567904, 'nullrrrr', 'Asisten Manager', 4500000, 450000, 4950000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tabel_gaji`
--
ALTER TABLE `tabel_gaji`
  ADD PRIMARY KEY (`Nik`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tabel_gaji`
--
ALTER TABLE `tabel_gaji`
  MODIFY `Nik` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1234567907;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
